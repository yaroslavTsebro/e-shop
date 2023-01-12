package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.command.customer.CreateCategory;
import com.technograd.technograd.web.exeption.DBException;
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
    private static final String SQL__FIND_CATEGORY_BY_NAME_UA = "SELECT * FROM category WHERE name_ua=?;";
    private static final String SQL__FIND_CATEGORY_BY_NAME_EN = "SELECT * FROM category WHERE name_en=?;";
    private static final String SQL__CREATE_CATEGORY = "INSERT INTO category (name_ua, name_en) VALUES(?, ?);";
    private static final String SQL__DELETE_CATEGORY = "DELETE FROM category WHERE id=?;";
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    public static List<Category> getAllCategories() throws DBException {
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
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return categories;
    }

    public static Category getCategoryById(int id) throws DBException {
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

    public static Category getCategoryByName(String name, String language) throws DBException {
        Category category = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getInstance().getConnection();
            CategoryMapper mapper = new CategoryMapper();

            if("ua".equals(language)){
                preparedStatement = connection.prepareStatement(SQL__FIND_CATEGORY_BY_NAME_UA);
            } else {
                preparedStatement = connection.prepareStatement(SQL__FIND_CATEGORY_BY_NAME_EN);
            }
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            category = mapper.mapRow(resultSet);
        } catch (Exception e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException();
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return category;
    }
    public static void createCategory(Category category) throws DBException {
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

    public static void deleteById(int id) throws DBException {
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
