package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.error.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompatibilityDAO {
    private static final String SQL__FIND_COMPATIBILITY_BY_ID = "SELECT * FROM compatibility WHERE id=?;";
    private static final String SQL__FIND_COMPATIBILITIES_BY_CATEGORY_ID = "SELECT * FROM compatibility WHERE category_id=?;";
    private static final String SQL__FIND_COMPATIBILITIES_BY_CHARACTERISTIC_ID = "SELECT * FROM characteristic WHERE characteristic_id=?;";
    private static final String SQL__CREATE_COMPATIBILITY = "INSERT INTO compatibility(category_id, characteristic_id) VALUES(?, ?);";

    public static void createCompatibility(Compatibility compatibility) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_COMPATIBILITY);
            preparedStatement.setInt(1, compatibility.getCategory().getId());
            preparedStatement.setInt(2, compatibility.getCharacteristic().getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection,preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection,preparedStatement);
        }
    }

    public static Compatibility findCompatibilityById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Compatibility compatibility = null;
        try{
            connection = DBManager.getInstance().getConnection();
            CompatibilityMapper mapper = new CompatibilityMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_COMPATIBILITY_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                compatibility = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return compatibility;
    }

    public static Compatibility findCompatibilityByCategoryId(int categoryId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Compatibility compatibility = null;
        try{
            connection = DBManager.getInstance().getConnection();
            CompatibilityMapper mapper = new CompatibilityMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_COMPATIBILITIES_BY_CATEGORY_ID);
            preparedStatement.setInt(1, categoryId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                compatibility = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return compatibility;
    }

    public static Compatibility findCompatibilityByCharacteristicId(int characteristicId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Compatibility compatibility = null;
        try{
            connection = DBManager.getInstance().getConnection();
            CompatibilityMapper mapper = new CompatibilityMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_COMPATIBILITIES_BY_CHARACTERISTIC_ID);
            preparedStatement.setInt(1, characteristicId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                compatibility = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return compatibility;
    }

    private static class CompatibilityMapper implements EntityMapper<Compatibility>{

        @Override
        public Compatibility mapRow(ResultSet rs) {

            try {
                Compatibility compatibility = new Compatibility();
                compatibility.setId(rs.getInt(Fields.ID));
                compatibility.setCategory( new CategoryDAO().getCategoryById(rs.getInt(Fields.COMPATIBILITY_CATEGORY_ID)));
                compatibility.setCharacteristic( new CharacteristicDAO().getCharacteristicById(rs.getInt(Fields.COMPATIBILITY_CHARACTERISTIC_ID)));
                return compatibility;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
