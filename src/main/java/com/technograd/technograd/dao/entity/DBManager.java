package com.technograd.technograd.dao.entity;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DBManager {

    private static DBManager instance;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        //log.info("DBmanager instance was created");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/technograd");
            con = ds.getConnection();
        } catch (NamingException ex) {
           // log.warning("Cannot get connection");
        }
        return con;
    }

    public void commitAndClose(Connection connection) {
        try {
            if (connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (SQLException e) {
            //log.warning("commitAndClose went bad");
            e.printStackTrace();
        }
    }

    public void commitAndClose(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                connection.close();
            }
            if (connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (SQLException e) {
           // log.warning("commitAndClose went bad");
            e.printStackTrace();
        }


    }


    public void commitAndClose(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }

            if (connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (SQLException e) {
            //log.warning("commitAndClose went bad");
            e.printStackTrace();
        }
    }

    public void closeResources(Connection con, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
           // log.warning("closeResources went bad");
            e.printStackTrace();
        }
    }

    public void closeResources(Connection con, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
           // log.warning("closeResources went bad");
            e.printStackTrace();
        }
    }

    public void rollbackAndClose(Connection con, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.rollback();
                con.close();
            }
        } catch (SQLException e) {
           // log.warning("rollbackAndClose went bad");
            e.printStackTrace();
        }
    }

    public void rollbackAndClose(Connection con, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (con != null) {
                con.rollback();
                con.close();
            }
        } catch (SQLException e) {
            //log.warning("rollbackAndClose went bad");
            e.printStackTrace();
        }
    }

}
