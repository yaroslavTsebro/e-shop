package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exсeption.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    private static final String SQL__CREATE_SUPPLIER = "INSERT INTO supplier(name, phone, email) VALUES (?, ?, ?) WHERE id = ?;";
    private static final String SQL__FIND_SUPPLIER_BY_ID = "SELECT * FROM supplier WHERE id=?;";
    private static final String SQL__FIND_ALL_SUPPLIERS = "SELECT * FROM supplier";
    private static final String SQL__UPDATE_SUPPLIER = "UPDATE FROM supplier(name, phone, email) VALUES (?, ?, ?);";

    public void createSupplier(Supplier supplier) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_SUPPLIER);
            preparedStatement.setString(1,supplier.getName());
            preparedStatement.setString(2,supplier.getPhone());
            preparedStatement.setString(3,supplier.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public Supplier getSupplierById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Supplier supplier = null;
        try{
            connection = DBManager.getInstance().getConnection();
            SupplierMapper mapper = new SupplierMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_SUPPLIER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                supplier = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return supplier;
    }

    public List<Supplier> getAllSuppliers() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Supplier> suppliers = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            SupplierMapper mapper = new SupplierMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_SUPPLIERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Supplier supplier = mapper.mapRow(resultSet);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return suppliers;
    }

    public void updateSupplier(Supplier supplier) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_SUPPLIER);
            preparedStatement.setString(1,supplier.getName());
            preparedStatement.setString(2,supplier.getPhone());
            preparedStatement.setString(3,supplier.getEmail());
            preparedStatement.setInt(4,supplier.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    private static class SupplierMapper implements EntityMapper<Supplier>{

        @Override
        public Supplier mapRow(ResultSet rs) {
            try {
                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt(Fields.ID));
                supplier.setName(rs.getString(Fields.SUPPLIER_NAME));
                supplier.setPhone(rs.getString(Fields.SUPPLIER_PHONE));
                supplier.setEmail(rs.getString(Fields.SUPPLIER_EMAIL));
                return supplier;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
