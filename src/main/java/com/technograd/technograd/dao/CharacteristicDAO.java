package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacteristicDAO {
    private static final String SQL__FIND_ALL_CHARACTERISTICS = "SELECT * FROM characteristic;";
    private static final String SQL__FIND_CHARACTERISTIC_BY_ID = "SELECT * FROM characteristic WHERE id=?;";
    private static final String SQL__FIND_CHARACTERISTIC_BY_NAME_UA = "SELECT * FROM characteristic WHERE id=?;";
    private static final String SQL__FIND_CHARACTERISTIC_BY_NAME_EN = "SELECT * FROM characteristic WHERE id=?;";
    private static final String SQL__CREATE_CHARACTERISTIC = "INSERT INTO characteristic (name_ua, name_en) VALUES(?, ?);";
    private static final String SQL__DELETE_CHARACTERISTIC = "DELETE FROM characteristic WHERE id=?;";

    public static List<Characteristic> getAllCharacteristics() throws DBException {
        List<Characteristic> characteristics = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CharacteristicMapper mapper = new CharacteristicMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_CHARACTERISTICS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Characteristic characteristic = mapper.mapRow(resultSet);
                characteristics.add(characteristic);
            }
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return characteristics;
    }

    public static Characteristic getCharacteristicById(int id) throws DBException {
        Characteristic characteristic = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CharacteristicMapper mapper = new CharacteristicMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_CHARACTERISTIC_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            characteristic = mapper.mapRow(resultSet);
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return characteristic;
    }

    public static Characteristic getCharacteristicByName(String name, String language) throws DBException {
        Characteristic characteristic = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CharacteristicMapper mapper = new CharacteristicMapper();

            if("ua".equals(language)){
                preparedStatement = connection.prepareStatement(SQL__FIND_CHARACTERISTIC_BY_NAME_UA);
            } else {
                preparedStatement = connection.prepareStatement(SQL__FIND_CHARACTERISTIC_BY_NAME_EN);
            }
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            characteristic = mapper.mapRow(resultSet);
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return characteristic;
    }
    public static void createCharacteristic(Characteristic characteristic) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_CHARACTERISTIC);
            preparedStatement.setString(1, characteristic.getNameUa());
            preparedStatement.setString(2, characteristic.getNameEn());
            preparedStatement.execute();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    public static void deleteCharacteristicById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__DELETE_CHARACTERISTIC);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }


    static class CharacteristicMapper implements EntityMapper<Characteristic> {


        @Override
        public Characteristic mapRow(ResultSet rs) {
            try {
                Characteristic characteristic = new Characteristic();
                characteristic.setId(rs.getInt(Fields.ID));
                characteristic.setNameUa(rs.getString(Fields.NAME_UA));
                characteristic.setNameEn(rs.getString(Fields.NAME_EN));
                return characteristic;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
