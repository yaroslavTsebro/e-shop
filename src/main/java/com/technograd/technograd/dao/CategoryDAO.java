package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.command.manager.category.CreateCategory;
import com.technograd.technograd.web.exсeption.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static final String SQL__FIND_ALL_CATEGORIES = "SELECT * FROM category;";
    private static final String SQL__FIND_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?;";
    private static final String SQL__FIND_CATEGORY_BY_SEARCH = "SELECT * FROM category WHERE name_ua LIKE ? OR name_en LIKE ?;";
    private static final String SQL__CREATE_CATEGORY = "INSERT INTO category (name_ua, name_en) VALUES(?, ?);";
    private static final String SQL__DELETE_CATEGORY = "DELETE FROM category WHERE id=?;";
    private static final String SQL__UPDATE_CATEGORY = "UPDATE category SET name_ua = ?, name_en = ? WHERE id =?";


    public List<Category> getAllCategories() throws DBException {
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CategoryMapper mapper = new CategoryMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_CATEGORIES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Category category = mapper.mapRow(resultSet);
                categories.add(category);
            }
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return categories;
    }

    public List<Category> searchCategories(String pattern) throws DBException {
        List<Category> categories = new ArrayList<>();
        PreparedStatement p = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            CategoryMapper mapper = new CategoryMapper();
            p = con.prepareStatement(SQL__FIND_CATEGORY_BY_SEARCH);
            pattern = "%" + pattern + "%";
            p.setString(1, pattern);
            p.setString(2, pattern);
            rs = p.executeQuery();
            while (rs.next()) {
                categories.add(mapper.mapRow(rs));
            }
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con, p, rs);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con, p, rs);
        }
        return categories;
    }


    public Category getCategoryById(int id) throws DBException {
        Category category = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CategoryMapper mapper = new CategoryMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_CATEGORY_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                category = mapper.mapRow(resultSet);
            }
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return category;
    }

    public void createCategory(Category category) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_CATEGORY);
            preparedStatement.setString(1, category.getNameUa());
            preparedStatement.setString(2, category.getNameEn());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
           DBManager.getInstance().closeResources(connection, preparedStatement);
        }
    }

    public void updateCategory(Category category) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_CATEGORY);
            preparedStatement.setString(1, category.getNameUa());
            preparedStatement.setString(2, category.getNameEn());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().closeResources(connection, preparedStatement);
        }
    }

    public void deleteById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__DELETE_CATEGORY);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }


    static class CategoryMapper implements EntityMapper<Category>{

        @Override
        public Category mapRow(ResultSet rs) {
            try {
                Category category = new Category();
                category.setId(rs.getInt(Fields.ID));
                category.setNameUa(rs.getString(Fields.NAME_UA));
                category.setNameEn(rs.getString(Fields.NAME_EN));
                return category;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
