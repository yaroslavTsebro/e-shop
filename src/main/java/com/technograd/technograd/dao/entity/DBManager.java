package com.technograd.technograd.dao.entity;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.naming.Context;
import java.sql.*;
import java.util.Properties;

public class DBManager {

    private static DBManager instance;
   private final  Logger log = LogManager.getLogger(getClass());
    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }


     public Connection getConnection() throws SQLException {
         Connection con = null;
         try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:");
             DataSource ds = (DataSource) envContext.lookup("jdbc/technograd");
             System.out.println(ds);
             con = ds.getConnection();
         } catch (NamingException ex) {
             System.out.println("system");
             System.out.println(ex);
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
            e.printStackTrace();
        }
    }

    public void commitAndClose(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (SQLException e) {
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
            e.printStackTrace();
        }
    }

}
