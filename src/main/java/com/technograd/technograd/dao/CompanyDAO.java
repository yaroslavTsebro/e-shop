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
    private static final String SQL__CREATE_COMPANY = "INSERT INTO category (name_ua, name_en, country_id) VALUES(?, ?, ?);";
    private static final String SQL__DELETE_COMPANY = "DELETE FROM company WHERE id=?;";
    private static final String SQL__FIND_COMPANY_BY_SEARCH = "SELECT * FROM company WHERE name_ua LIKE ? OR name_en LIKE ?;";
    private static final String SQL__UPDATE_COMPANY = "UPDATE category SET name_ua = ?, name_en = ? WHERE id =?";

    public static List<Company> getAllCompanies() throws DBException {
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

    public static void updateCompany(Company company) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_COMPANY);
            preparedStatement.setString(1, company.getNameUa());
            preparedStatement.setString(2, company.getNameEn());
            preparedStatement.setInt(3, company.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().closeResources(connection, preparedStatement);
        }
    }

    public static List<Company> searchCompanies(String pattern) throws DBException {
        List<Company> companyList = new ArrayList<>();
        PreparedStatement p = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            CompanyMapper mapper = new CompanyMapper();
            p = con.prepareStatement(SQL__FIND_COMPANY_BY_SEARCH);
            pattern = "%" + pattern + "%";
            p.setString(1, pattern);
            p.setString(2, pattern);
            rs = p.executeQuery();
            while (rs.next()) {
                companyList.add(mapper.mapRow(rs));
            }
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con, p, rs);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con, p, rs);
        }
        return companyList;
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
            preparedStatement.setInt(3, company.getCountry().getId());
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
                company.setCountry(CountryDAO.getCompanyById(rs.getInt(Fields.ID)));
                return company;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
