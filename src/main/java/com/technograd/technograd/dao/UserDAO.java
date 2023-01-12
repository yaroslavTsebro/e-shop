package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;


import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String SQL__CREATE_USER = "INSERT INTO user(lastname, name, email ,post, password, salt, local_name) VALUES(?, ?, ?, ? ,? ,?, ?);";
    private static final String SQL__FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?;";
    private static final String SQL__FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?;";
    private static final String SQL__FIND_ALL_USERS = "SELECT * FROM user;";
    private static final String SQL__UPDATE_USER_LANGUAGE = "UPDATE user SET locale_name=? WHERE id=?;";
    private static final String SQL__FIRST_PART_OF_EVENT = "CREATE EVENT IF NOT EXISTS delete_code";
    private static final String SQL__SECOND_PART_OF_EVENT = " ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 1 HOUR DO DELETE FROM user_details WHERE code=?;";
    private static final String SQL__ADD_CONFIRMATION_CODE = "INSERT INTO user_details(user_id, code, salt) VALUE (?, ?, ?);";
    private static final String SQL__GET_SALT_FROM_USER_DETAILS = "SELECT salt FROM user_details WHERE user_id=?;";
    private static final String SQL__GET_CODE_FROM_USER_DETAILS = "SELECT code FROM user_details WHERE user_id=?;";
    private static final String SQL__UPDATE_USER_PASSWORD = "UPDATE user SET password=?, salt=? WHERE id=?;";
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

    public static String getSaltFromUserDetails(int userId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String salt = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__GET_SALT_FROM_USER_DETAILS);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                salt = resultSet.getString(Fields.USER_DETAILS_SALT);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return salt;
    }

    public static String getCodeFromUserDetails(int userId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String code = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__GET_CODE_FROM_USER_DETAILS);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                code = resultSet.getString(Fields.USER_DETAILS_CODE);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return code;
    }
    public static void addConfirmationCode(UserDetails details) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(SQL__DROP_CONFIRMATION_CODE);
            preparedStatement.setInt(1,  details.getUserId());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(SQL__ADD_CONFIRMATION_CODE);
            preparedStatement.setInt(1, details.getUserId());
            preparedStatement.setString(2, details.getCode());
            preparedStatement.setString(2, details.getCode());
            preparedStatement.setString(3, details.getSalt());
            preparedStatement.execute();

            String event = SQL__FIRST_PART_OF_EVENT +
                    details.getSalt().substring(new SecureRandom().nextInt(details.getSalt().length() / 2)) +
                    SQL__SECOND_PART_OF_EVENT;
            preparedStatement = connection.prepareStatement(event);
            preparedStatement.setString(1, details.getCode());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().closeResources(connection, preparedStatement);
        }
    }

    public static void createUser(User user) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();

            preparedStatement = connection.prepareStatement(SQL__CREATE_USER);
            preparedStatement.setString(1, user.getLastname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPost().toString());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getSalt());
            preparedStatement.setString(7, "en");
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static void updateUserLanguage(int id, String language) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_USER_LANGUAGE);
            preparedStatement.setString(1, language);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static User getUserById(int id) throws DBException {
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

    public static User getUserByEmail(String email) throws DBException {
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

    public static List<User> getAllUsers() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();
        try{
            connection = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_USERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = mapper.mapRow(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return users;
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
