package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCharacteristicDAO {
    private static final String SQL__FIND_PRODUCT_CHARACTERISTICS_BY_PRODUCT_ID =
            "SELECT * FROM product_characteristic WHERE product_id=?;";
    private static final String SQL__FIND_PRODUCT_CHARACTERISTIC_BY_ID =
            "SELECT * FROM product_characteristic WHERE id=?;";
    private static final String SQL__FIND_ALL_PRODUCT_CHARACTERISTICS =
            "SELECT * FROM product_characteristic;";
    private static final String SQL__CREATE_PRODUCT_CHARACTERISTIC =
            "INSERT INTO product_characteristic(product_id, compatibility_id, value) VALUES(?, ?, ?);";

    public static List<ProductCharacteristic> findProductCharacteristicsByProductId(int productId) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProductCharacteristic> productCharacteristics = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            ProductCharacteristicMapper mapper = new ProductCharacteristicMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_PRODUCT_CHARACTERISTICS_BY_PRODUCT_ID);
            preparedStatement.setInt(1, productId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productCharacteristics.add(mapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return productCharacteristics;
    }
    public static ProductCharacteristic findProductCharacteristicById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ProductCharacteristic productCharacteristic = null;
        try{
            connection = DBManager.getInstance().getConnection();
            ProductCharacteristicMapper mapper = new ProductCharacteristicMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_PRODUCT_CHARACTERISTIC_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productCharacteristic = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return productCharacteristic;
    }

    public static List<ProductCharacteristic> findAllProductCharacteristics() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProductCharacteristic> productCharacteristics = new ArrayList<>();
        try{
            connection = DBManager.getInstance().getConnection();
            ProductCharacteristicMapper mapper = new ProductCharacteristicMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_PRODUCT_CHARACTERISTICS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productCharacteristics.add(mapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return productCharacteristics;
    }

    public static void createProductCharacteristic(ProductCharacteristic productCharacteristic) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_PRODUCT_CHARACTERISTIC);
            preparedStatement.setInt(1, productCharacteristic.getProductId());
            preparedStatement.setInt(2, productCharacteristic.getCompatibility().getId());
            preparedStatement.setString(3, productCharacteristic.getValue());
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }


    private static class ProductCharacteristicMapper implements EntityMapper<ProductCharacteristic>{

        @Override
        public ProductCharacteristic mapRow(ResultSet rs) {
            try {
                ProductCharacteristic productCharacteristic = new ProductCharacteristic();
                productCharacteristic.setId(rs.getInt(Fields.ID));
                productCharacteristic.setId(rs.getInt(Fields.PRODUCT_CHARACTERISTIC_PRODUCT_ID));
                productCharacteristic.setCompatibility(new CompatibilityDAO().
                        findCompatibilityById(rs.getInt(Fields.PRODUCT_CHARACTERISTIC_COMPATIBILITY_ID)));
                productCharacteristic.setValue(rs.getString(Fields.PRODUCT_CHARACTERISTIC_VALUE));
                return  productCharacteristic;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
