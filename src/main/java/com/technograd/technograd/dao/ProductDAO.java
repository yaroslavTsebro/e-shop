package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.error.DBException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String SQL__FIND_PRODUCTS_BY_COMPANY = "SELECT * FROM product WHERE product.company_id =?;";
    private static final String SQL__FIND_PRODUCTS_BY_CATEGORY = "SELECT * FROM product WHERE product.category =?;";
    private static final String SQL__FIND_REDUCED_PRODUCT_BY_ID = "SELECT (id, name_ua, name_en, title_ua, title_en, price) FROM product WHERE id=?;";
    private static final String SQL__FIND_ALL_PRODUCTS = "SELECT * FROM product;";
    private static final String SQL__FIND_PRODUCT_BY_ID = "SELECT * FROM product WHERE product.id=?;";
    private static final String SQL__CREATE_PRODUCT = "INSERT INTO product(name_ua, name_en, price, weight, category_id," +
            " company_id, count, warranty, title_ua, title_en, description_ua, description_en) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static void createProduct(Product product) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_PRODUCT);

            preparedStatement.setString(1, product.getNameUa());
            preparedStatement.setString(2, product.getNameEn());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setInt(4, product.getWeight());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.setInt(6, product.getCompany().getId());
            preparedStatement.setInt(7, product.getCount());
            preparedStatement.setInt(8, product.getWarranty());
            preparedStatement.setString(9, product.getTitleUa());
            preparedStatement.setString(10, product.getTitleEn());
            preparedStatement.setString(11, product.getDescriptionUa());
            preparedStatement.setString(12, product.getDescriptionEn());

            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static List<Product> getAllProducts() throws DBException {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getInstance().getConnection();
            ProductMapper mapper = new ProductMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_ALL_PRODUCTS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                products.add(mapper.mapRow(resultSet));
            }

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return products;
    }

    public static Product getProductById(int id) throws DBException {
        Product product = new Product();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getInstance().getConnection();
            ProductMapper mapper = new ProductMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                product = mapper.mapRow(resultSet);
            }

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return product;
    }

    public static Product getReducedProductById(int id) throws DBException {
        Product product = new Product();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_REDUCED_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                product.setId(resultSet.getInt(Fields.ID));
                product.setNameUa(resultSet.getString(Fields.NAME_UA));
                product.setNameEn(resultSet.getString(Fields.NAME_EN));
                product.setTitleUa(resultSet.getString(Fields.PRODUCT_TITLE_UA));
                product.setTitleEn(resultSet.getString(Fields.PRODUCT_TITLE_EN));
                product.setPrice(resultSet.getBigDecimal(Fields.PRODUCT_PRICE));
            }

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return product;
    }
private static class ProductMapper implements EntityMapper<Product>{
    @Override
    public Product mapRow(ResultSet rs) {
        try{
            Product product = new Product();
            product.setId(rs.getInt(Fields.ID));
            product.setNameUa(rs.getString(Fields.NAME_UA));
            product.setNameEn(rs.getString(Fields.NAME_EN));
            product.setPrice(rs.getBigDecimal(Fields.PRODUCT_PRICE));
            product.setWeight(rs.getInt(Fields.PRODUCT_WEIGHT));
            product.setCategory(new CategoryDAO().getCategoryById(rs.getInt(Fields.PRODUCT_CATEGORY_ID)));
            product.setCompany(new CompanyDAO().getCompanyById(rs.getInt(Fields.PRODUCT_COMPANY_ID)));
            product.setCount(rs.getInt(Fields.PRODUCT_COUNT));
            product.setWarranty(rs.getInt(Fields.PRODUCT_WARRANTY));
            product.setTitleUa(rs.getString(Fields.PRODUCT_TITLE_UA));
            product.setTitleEn(rs.getString(Fields.PRODUCT_TITLE_EN));
            product.setDescriptionUa(rs.getString(Fields.PRODUCT_DESCRIPTION_UA));
            product.setDescriptionEn(rs.getString(Fields.PRODUCT_DESCRIPTION_EN));
            product.setPhoto(new PhotoDAO().getPhotosById(rs.getInt(Fields.ID)));
            product.setProductCharacteristics(new ProductCharacteristicDAO().findProductCharacteristicsByProductId(rs.getInt(Fields.ID)));
            return product;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

}
