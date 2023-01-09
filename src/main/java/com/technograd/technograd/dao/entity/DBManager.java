package com.technograd.technograd.dao.entity;

import com.technograd.technograd.web.error.DBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

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
            String url = "jdbc:postgresql://localhost:5432/technograd";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "zsbldqpk56");
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, props);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
