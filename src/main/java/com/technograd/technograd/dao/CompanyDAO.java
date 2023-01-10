package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class CompanyDAO {
    private static final String SQL__FIND_ALL_COMPANIES = "SELECT * FROM company;";
    private static final String SQL__FIND_COMPANY_BY_ID = "SELECT * FROM company WHERE id=?;";
    private static final String SQL__FIND_COMPANY_BY_NAME_UA = "SELECT * FROM company WHERE id=?;";
    private static final String SQL__FIND_COMPANY_BY_NAME_EN = "SELECT * FROM company WHERE id=?;";
    private static final String SQL__CREATE_COMPANY = "INSERT INTO category (name_ua, name_en, country_ua, country_en) VALUES(?, ?, ?, ?);";
    private static final String SQL__DELETE_COMPANY = "DELETE FROM company WHERE id=?;";

    public List<Company> getAllCompanies() throws DBException {
        List<Company>  companies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DBManager.getInstance().getConnection();
            CompanyMapper mapper = new CompanyMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_COMPANIES);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Company company = mapper.mapRow(resultSet);
                companies.add(company);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return companies;
    }

    public static Company getCompanyById(int id) throws DBException {
        Company company = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CompanyMapper mapper = new CompanyMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_COMPANY_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            company = mapper.mapRow(resultSet);
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return company;
    }

    public static Company getCompanyByName(int name, String language) throws DBException {
        Company company = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CompanyMapper mapper = new CompanyMapper();
            if("ua".equals(language)){
                preparedStatement = connection.prepareStatement(SQL__FIND_COMPANY_BY_NAME_UA);
            } else {
                preparedStatement = connection.prepareStatement(SQL__FIND_COMPANY_BY_NAME_EN);
            }
            preparedStatement.setInt(1, name);
            resultSet = preparedStatement.executeQuery();
            company = mapper.mapRow(resultSet);
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return company;
    }

    public static void createCompany(Company company) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_COMPANY);
            preparedStatement.setString(1, company.getNameUa());
            preparedStatement.setString(2, company.getNameEn());
            preparedStatement.setString(3, company.getCountryUa());
            preparedStatement.setString(4, company.getCountryEn());
            preparedStatement.execute();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static void deleteCompany(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__DELETE_COMPANY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    static class CompanyMapper implements EntityMapper<Company>{

        @Override
        public Company mapRow(ResultSet rs) {
            try {
                Company company = new Company();
                company.setId(rs.getInt(Fields.ID));
                company.setNameUa(rs.getString(Fields.NAME_UA));
                company.setNameEn(rs.getString(Fields.NAME_EN));
                company.setCountryUa(rs.getString(Fields.COMPANY_COUNTRY_UA));
                company.setCountryEn(rs.getString(Fields.COMPANY_COUNTRY_EN));
                return company;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
