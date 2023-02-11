package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exсeption.DBException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String SQL__CREATE_USER = "INSERT INTO \"user\"(lastname, name, email ,post, password, salt, local_name) VALUES(?, ?, ?, 'CUSTOMER' ,? ,?, ?);";
    private static final String SQL__FIND_USER_BY_ID = "SELECT * FROM \"user\" WHERE id = ?;";
    private static final String SQL__FIND_USER_BY_EMAIL = "SELECT * FROM \"user\" WHERE email = ?;";
    private static final String SQL__UPDATE_USER_LANGUAGE = "UPDATE \"user\" SET local_name=? WHERE id=?;";
    private static final String SQL__ADD_CONFIRMATION_CODE = "INSERT INTO user_details(user_id, code, salt, created_at) VALUES(?, ?, ?, current_timestamp);";
    private static final String SQL__GET_SALT_FROM_USER_DETAILS = "SELECT salt FROM user_details WHERE user_id=? AND EXTRACT(MINUTE FROM CURRENT_TIMESTAMP - created_at) <= 59;";
    private static final String SQL__GET_CODE_FROM_USER_DETAILS = "SELECT code FROM user_details WHERE user_id=? AND EXTRACT(MINUTE FROM CURRENT_TIMESTAMP - created_at) <= 59;";
    private static final String SQL__UPDATE_USER_PASSWORD = "UPDATE \"user\" SET password=?, salt=? WHERE id=?;";
    private static final String SQL__DROP_CONFIRMATION_CODE = "DELETE FROM user_details WHERE user_id=?;";

    public void updateUserPassword(String newSecurePassword, String newSalt, int userId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_USER_PASSWORD);
            preparedStatement.setString(1, newSecurePassword);
            preparedStatement.setString(2, newSalt);
            preparedStatement.setInt(3, userId);
            preparedStatement.execute();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public void updateUserLanguageToUa(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_USER_LANGUAGE);
            preparedStatement.setString(1, "ua");
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public void updateUserLanguageToEn(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_USER_LANGUAGE);
            preparedStatement.setString(1, "en");
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public void createUser(User user) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(SQL__CREATE_USER);
            preparedStatement.setString(1, user.getLastname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getSalt());
            preparedStatement.setString(6, "en");
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public User getUserById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            connection = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return user;
    }

    public User getReducedUserById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;
        try{
            connection = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                User mappedUser = new User();
                mappedUser.setId(rs.getInt(Fields.ID));
                mappedUser.setLastname(rs.getString(Fields.USER_LASTNAME));
                mappedUser.setName(rs.getString(Fields.USER_NAME));
                mappedUser.setEmail(rs.getString(Fields.USER_EMAIL));
                mappedUser.setPost(rs.getString(Fields.USER_POST));
                mappedUser.setLocaleName(rs.getString(Fields.USER_LANGUAGE));
                user = mappedUser;
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return user;
    }

    public User getUserByEmail(String email) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            connection = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return user;
    }

    public void addConfirmationCode(int userId, String salt, String code) throws DBException {
        PreparedStatement p = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            p = con.prepareStatement(SQL__DROP_CONFIRMATION_CODE);
            p.setInt(1, userId);
            p.execute();

            p = con.prepareStatement(SQL__ADD_CONFIRMATION_CODE);
            p.setInt(1, userId);
            p.setString(2, code);
            p.setString(3, salt);
            p.execute();

        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con, p);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con, p);
        }
    }

    public String getCode(int userId) throws DBException {
        PreparedStatement p = null;
        Connection con = null;
        ResultSet rs = null;
        String code = null;
        try {
            con = DBManager.getInstance().getConnection();
            p = con.prepareStatement(SQL__GET_CODE_FROM_USER_DETAILS);
            p.setInt(1, userId);
            rs = p.executeQuery();
            if (rs.next()) {
                code = rs.getString(Fields.USER_DETAILS_CODE);
            }
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con, p, rs);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con, p, rs);
        }
        return code;
    }

    public String getSalt(int userId) throws DBException {
        PreparedStatement p = null;
        Connection con = null;
        ResultSet rs = null;
        String salt = null;
        try {
            con = DBManager.getInstance().getConnection();
            p = con.prepareStatement(SQL__GET_SALT_FROM_USER_DETAILS);
            p.setInt(1, userId);
            rs = p.executeQuery();
            if (rs.next()) {
                salt = rs.getString(Fields.USER_SALT);
            }
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con, p, rs);
            throw new DBException(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con, p, rs);
        }
        return salt;
    }



    private static class UserMapper implements EntityMapper<User>{

        @Override
        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setId(rs.getInt(Fields.ID));
                user.setLastname(rs.getString(Fields.USER_LASTNAME));
                user.setName(rs.getString(Fields.USER_NAME));
                user.setEmail(rs.getString(Fields.USER_EMAIL));
                user.setPost(rs.getString(Fields.USER_POST));
                user.setPassword(rs.getString(Fields.USER_PASSWORD));
                user.setSalt(rs.getString(Fields.USER_SALT));
                user.setLocaleName(rs.getString(Fields.USER_LANGUAGE));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
