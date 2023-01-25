package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String SQL__FIND_PRODUCTS_BY_COMPANY = "SELECT * FROM product WHERE product.company_id =?;";
    private static final String SQL__FIND_PRODUCTS_BY_CATEGORY = "SELECT * FROM product WHERE product.category =?;";
    private static final String SQL__FIND_REDUCED_PRODUCT_BY_ID = "SELECT (id, name_ua, name_en, title_ua, title_en, price) FROM product WHERE id=?";
    private static final String SQL__FIND_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String SQL__FIND_PRODUCT_BY_ID = "SELECT * FROM product WHERE product.id=?;";
    private static final String SQL__CREATE_PRODUCT = "INSERT INTO product(name_ua, name_en, price, weigth, category_id," +
            " company_id, count, warranty, title_ua, title_en, description_ua, description_en) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
    private static final String SQL__CREATE_PRODUCT_CHARACTERISTIC =
            "INSERT INTO product_characteristic(product_id, compatibility_id, value) VALUES(?, ?, ?);";
    private static final String SQL__CREATE_PHOTO = "INSERT INTO photo(product_id, name) VALUES (?, ?);";
    private static final String SQL__CREATE_COMPATIBILITY = "INSERT INTO compatibility(category_id, characteristic_id) VALUES(?, ?) RETURNING id;";
    private static final String SQL__UPDATE_PRODUCT = "UPDATE product SET name_ua = ?, name_en = ?, price = ?," +
            "weigth = ?, category_id= ?, company_id= ?, count= ?, warranty= ?, title_ua= ?, title_en= ?, description_ua= ?, description_en= ?  WHERE id =?;";
    private static final String SQL__UPDATE_COUNT_BY_ID = "UPDATE product SET count = ? WHERE id = ?;";

    public static void updateCountById(int id, int count) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_COUNT_BY_ID);
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    public static void updateProduct(Product product) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__UPDATE_PRODUCT);
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
            preparedStatement.setInt(13, product.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }
    //I`ve done it because of inserting rubbish data with case with trouble
    public static void createProductAndPhotosAndCharacteristics(Product product, List<Characteristic> characteristics) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            //Create product
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
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product.setId(resultSet.getInt(1));
            }

            //Create photos
            for (Photo photo : product.getPhotos()) {
                preparedStatement = connection.prepareStatement(SQL__CREATE_PHOTO);
                preparedStatement.setInt(1, product.getId());
                preparedStatement.setString(2, photo.getName());
                preparedStatement.execute();
            }

            //Create compatibility and productCharacteristic
            for (int i = 0; i < characteristics.size(); i++) {
                preparedStatement = connection.prepareStatement(SQL__CREATE_COMPATIBILITY);
                preparedStatement.setInt(1, product.getCategory().getId());
                preparedStatement.setInt(2, characteristics.get(i).getId());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    characteristics.get(i).setId(resultSet.getInt(1));
                }
                preparedStatement = connection.prepareStatement(SQL__CREATE_PRODUCT_CHARACTERISTIC);
                preparedStatement.setInt(1, product.getId());
                preparedStatement.setInt(2,characteristics.get(i).getId());
                preparedStatement.setString(3,product.getProductCharacteristics().get(i).getValue());
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static List<Product> getAllReducedProducts(String query) throws DBException {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt(Fields.ID));
                product.setNameUa(rs.getString(Fields.NAME_UA));
                product.setNameEn(rs.getString(Fields.NAME_EN));
                product.setPrice(rs.getBigDecimal(Fields.PRODUCT_PRICE));
                product.setPhotos(new PhotoDAO().getFirstPhotoByProductId(rs.getInt(Fields.ID)));
                products.add(product);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, rs);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, rs);
        }
        return products;
    }
    public static String menuQueryBuilder(int company,int category, String sortCondition){
        String queryBase = SQL__FIND_ALL_PRODUCTS;

        if(company >0){
            queryBase += " WHERE company_id =" + company;
        }
        if(company >0 && category >0){
            queryBase += " AND category_id =" + category;
        }
        if(category >0 && company <=0){
            queryBase += " WHERE category_id =" + category;
        }

        if(sortCondition != null && !sortCondition.isEmpty()){
            if(sortCondition.equals("priceAsc")){
                queryBase += " ORDER BY price ASC";
            } else if (sortCondition.equals("priceDesc")) {
                queryBase += " ORDER BY price DESC";
            } else if (sortCondition.equals("nameAsc")) {
                queryBase += " ORDER BY name_en ASC";
            } else if (sortCondition.equals("nameDesc")) {
                queryBase += " ORDER BY name_en DESC";
            } else if (sortCondition.equals("avaliable")) {
                queryBase += " ORDER BY count ASC";
            } else {

            }
        }
        queryBase += ";";
        return queryBase;
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
            product.setPhotos(new PhotoDAO().getPhotosById(rs.getInt(Fields.ID)));
            product.setProductCharacteristics(new ProductCharacteristicDAO().findProductCharacteristicsByProductId(rs.getInt(Fields.ID)));
            return product;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

}
