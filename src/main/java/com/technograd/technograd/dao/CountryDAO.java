package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exсeption.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
    private static final String SQL__FIND_ALL_COUNTRIES = "SELECT * FROM country;";
    private static final String SQL__FIND_COUNTRY_BY_ID = "SELECT * FROM country WHERE id=?;";
    private static final String SQL__UPDATE_COUNTRY = "UPDATE country SET name_ua = ?, name_en = ? WHERE id =?";
    private static final String SQL__CREATE_COUNTRY = "INSERT INTO country (name_ua, name_en) VALUES(?, ?);";

    public List<Country> getAllCountries() throws DBException {
        List<Country>  countries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DBManager.getInstance().getConnection();
            CountryMapper mapper = new CountryMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_COUNTRIES);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Country country = mapper.mapRow(resultSet);
                countries.add(country);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return countries;
    }

    public Country getCountryById(int id) throws DBException {
        Country country = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CountryMapper mapper = new CountryMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_COUNTRY_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                country = mapper.mapRow(resultSet);
            }
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return country;
    }

    public void updateCountry(Country country) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_COUNTRY);
            preparedStatement.setString(1, country.getNameUa());
            preparedStatement.setString(2, country.getNameEn());
            preparedStatement.setInt(3, country.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().closeResources(connection, preparedStatement);
        }
    }

    public void createCountry(Country country) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_COUNTRY);
            preparedStatement.setString(1, country.getNameUa());
            preparedStatement.setString(2, country.getNameEn());
            preparedStatement.execute();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    static class CountryMapper implements EntityMapper<Country> {
        @Override
        public Country mapRow(ResultSet rs) {
            try {
                Country country = new Country();
                country.setId(rs.getInt(Fields.ID));
                country.setNameUa(rs.getString(Fields.NAME_UA));
                country.setNameEn(rs.getString(Fields.NAME_EN));
                return country;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
