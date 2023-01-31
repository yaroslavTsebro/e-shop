package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListIntendDAO {
    private static final String SQL__CREATE_LIST_INTEND = "INSERT INTO list_intend(intend_id, count, product_id, product_price) VALUES(?, ?, ?, ?);";
    private static final String SQL__DELETE_LIST_INTEND_BY_ID = "DELETE FROM list_intend WHERE id=?;";
    private static final String SQL__UPDATE_COUNT_IN_LIST_INTEND_BY_ID = "UPDATE list_intend SET count=? WHERE id=?";
    private static final String SQL__UPDATE_PRICE_IN_LIST_INTEND_BY_ID = "UPDATE list_intend SET product_price=? WHERE id=?";
    private static final String SQL__FIND_ALL_LIST_INTENDS_BY_INTEND_ID = "SELECT * FROM list_intend WHERE intend_id=?;";

    public static List<ListIntend> getAllListIntendsByIntendId(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ListIntend> intends = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_LIST_INTENDS_BY_INTEND_ID);
            ListIntendMapper mapper = new ListIntendMapper();
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ListIntend listIntend = mapper.mapRow(resultSet);
                intends.add(listIntend);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return intends;
    }
    public static void updateCountInListIntendById( int id, int count) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_COUNT_IN_LIST_INTEND_BY_ID);
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static void updatePriceInListIntendById(BigDecimal price, int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_PRICE_IN_LIST_INTEND_BY_ID);
            preparedStatement.setBigDecimal(1, price);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static void deleteListIntendById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__DELETE_LIST_INTEND_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    public static void createListIntend(int intendId, int count, int productId, BigDecimal productPrice) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_LIST_INTEND);
            preparedStatement.setInt(1, intendId);
            preparedStatement.setInt(2, count);
            preparedStatement.setInt(3, productId);
            preparedStatement.setBigDecimal(4, productPrice);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    private static class ListIntendMapper implements EntityMapper<ListIntend>{

        @Override
        public ListIntend mapRow(ResultSet rs) {
            try {
                ListIntend listIntend = new ListIntend();
                listIntend.setId(rs.getInt(Fields.ID));
                listIntend.setIntendId(rs.getInt(Fields.LIST_INTEND_INTEND_ID));
                listIntend.setCount(rs.getInt(Fields.LIST_INTEND_COUNT));
                listIntend.setProduct(new ProductDAO().getReducedProductById(rs.getInt(Fields.LIST_INTEND_PRODUCT_ID)));
                listIntend.setProductPrice(rs.getBigDecimal(Fields.LIST_INTEND_PRODUCT_PRICE));
                return listIntend;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
