package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exсeption.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacteristicDAO {
    private static final String SQL__FIND_ALL_CHARACTERISTICS = "SELECT * FROM characteristic;";
    private static final String SQL__FIND_CHARACTERISTIC_BY_ID = "SELECT * FROM characteristic WHERE id=?;";
    private static final String SQL__CREATE_CHARACTERISTIC = "INSERT INTO characteristic (name_ua, name_en) VALUES(?, ?);";
    private static final String SQL__DELETE_CHARACTERISTIC = "DELETE FROM characteristic WHERE id=?;";
    private static final String SQL__SEARCH_CHARACTERISTIC = "SELECT * FROM characteristic WHERE name_ua LIKE ? OR name_en LIKE ?;";
    private static final String SQL__UPDATE_CHARACTERISTIC = "UPDATE characteristic SET name_ua = ?, name_en = ? WHERE id =?";

    public void updateCharacteristic(Characteristic characteristic) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_CHARACTERISTIC);
            preparedStatement.setString(1, characteristic.getNameUa());
            preparedStatement.setString(2, characteristic.getNameEn());
            preparedStatement.setInt(3, characteristic.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public List<Characteristic> searchCharacteristic(String pattern) throws DBException {
        List<Characteristic> characteristicList = new ArrayList<>();
        PreparedStatement p = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            CharacteristicMapper mapper = new CharacteristicMapper();
            p = con.prepareStatement(SQL__SEARCH_CHARACTERISTIC);
            pattern = "%" + pattern + "%";
            p.setString(1, pattern);
            p.setString(2, pattern);
            rs = p.executeQuery();
            while (rs.next()) {
                characteristicList.add(mapper.mapRow(rs));
            }
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con, p, rs);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con, p, rs);
        }
        return characteristicList;
    }


    public List<Characteristic> getAllCharacteristics() throws DBException {
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

    public Characteristic getCharacteristicById(int id) throws DBException {
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
            while(resultSet.next()){
                characteristic = mapper.mapRow(resultSet);
            }
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return characteristic;
    }

    public void createCharacteristic(Characteristic characteristic) throws DBException {
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
    public void deleteCharacteristicById(int id) throws DBException {
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
