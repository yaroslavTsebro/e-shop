package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntendDAO {
    private static final String SQL__CREATE_INTEND_SENDING = "INSERT INTO intend(start_date, user_id, sending_or_receiving, address, condition)" +
            "VALUES (current_timestamp, ?, 'SENDING', '', 'CART');";
    private static final String SQL__UPDATE_INTEND_CONDITION = "UPDATE intend SET condition = ? WHERE id=?;";
    private static final String SQL__CREATE_INTEND_RECEIVING = "INSERT INTO intend(start_date, supplier_id, employee_id, sending_or_receiving, address, condition)" +
            "VALUES (current_timestamp, ?, ?, 'RECEIVING', 'STORAGE', 'NEW');";

    private static final String SQL__FIND_INTEND_BY_ID = "SELECT * FROM intend WHERE id=?;";
    private static final String SQL__FIND_CART_BY_ID = "SELECT * FROM intend WHERE user_id=? AND condition='CART';";
    private static final String SQL__CHANGE_CART_INTO_INTEND = "UPDATE intend SET start_date = current_timestamp, condition = 'NEW', address=? WHERE id=?;";
    private static final String SQL__FIND_RECEIVING_INTEND = "SELECT * FROM intend WHERE sending_or_receiving = 'RECEIVING';";
    private static final String SQL__FIND_SENDING_INTEND = "SELECT * FROM intend WHERE sending_or_receiving = 'SENDING';";

    public static void createIntendSending(int userId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_INTEND_SENDING);
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static List<Intend> findAllReceivings() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Intend> intendList = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_RECEIVING_INTEND);
            preparedStatement.executeQuery();
            while (rs.next()){
                Intend intend = new Intend();
                intend.setId(rs.getInt(Fields.ID));
                intend.setStartDate(rs.getDate(Fields.INTEND_START_DATE));
                intend.setEndDate(rs.getDate(Fields.INTEND_END_DATE));
                intend.setUserId(rs.getInt(Fields.INTEND_USER_ID));
                intend.setSupplierId(rs.getInt(Fields.INTEND_SUPPLIER_ID));
                intend.setEmployeeId(rs.getInt(Fields.INTEND_EMPLOYEE_ID));
                intend.setSendingOrReceiving(rs.getString(Fields.INTEND_SENDING_OR_RECEIVING));
                intend.setAddress(rs.getString(Fields.INTEND_ADDRESS));
                intend.setCondition(rs.getString(Fields.INTEND_CONDITION));
                intendList.add(intend);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return intendList;
    }

    public static List<Intend> findAllSendings() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Intend> intendList = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_SENDING_INTEND);
            preparedStatement.executeQuery();
            while (rs.next()){
                Intend intend = new Intend();
                intend.setId(rs.getInt(Fields.ID));
                intend.setStartDate(rs.getDate(Fields.INTEND_START_DATE));
                intend.setEndDate(rs.getDate(Fields.INTEND_END_DATE));
                intend.setUserId(rs.getInt(Fields.INTEND_USER_ID));
                intend.setSupplierId(rs.getInt(Fields.INTEND_SUPPLIER_ID));
                intend.setEmployeeId(rs.getInt(Fields.INTEND_EMPLOYEE_ID));
                intend.setSendingOrReceiving(rs.getString(Fields.INTEND_SENDING_OR_RECEIVING));
                intend.setAddress(rs.getString(Fields.INTEND_ADDRESS));
                intend.setCondition(rs.getString(Fields.INTEND_CONDITION));
                intendList.add(intend);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return intendList;
    }

    public static void changeCartIntoIntend(String address, int cartId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CHANGE_CART_INTO_INTEND);
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, cartId);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    public static void updateIntendCondition(int id, Condition condition) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_INTEND_CONDITION);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, condition.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static void createIntendReceiving(Intend intend) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_INTEND_RECEIVING);
            preparedStatement.setDate(1, intend.getStartDate());
            preparedStatement.setInt(2, intend.getSupplierId());
            preparedStatement.setInt(3, intend.getEmployeeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    public static Intend findIntendById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_INTEND_BY_ID);
            IntendMapper mapper = new IntendMapper();
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            while (resultSet.next()){
                intend = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return intend;
    }
    public static Intend findCartById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Intend intend = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_CART_BY_ID);
            IntendMapper mapper = new IntendMapper();
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                intend = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return intend;
    }


    private static class IntendMapper implements EntityMapper<Intend>{
        @Override
        public Intend mapRow(ResultSet rs) {
            try {
                Intend intend = new Intend();
                intend.setId(rs.getInt(Fields.ID));
                intend.setStartDate(rs.getDate(Fields.INTEND_START_DATE));
                intend.setEndDate(rs.getDate(Fields.INTEND_END_DATE));
                intend.setUserId(rs.getInt(Fields.INTEND_USER_ID));
                intend.setSupplierId(rs.getInt(Fields.INTEND_SUPPLIER_ID));
                intend.setEmployeeId(rs.getInt(Fields.INTEND_EMPLOYEE_ID));
                intend.setSendingOrReceiving(rs.getString(Fields.INTEND_SENDING_OR_RECEIVING));
                intend.setAddress(rs.getString(Fields.INTEND_ADDRESS));
                intend.setCondition(rs.getString(Fields.INTEND_CONDITION));
                intend.setListIntends(new ListIntendDAO().getAllListIntendsByIntendId(rs.
                        getInt(Fields.ID)));
                return intend;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
