package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDAO {
    private static final String SQL__FIND_PHOTOS_BY_PRODUCT_ID = "SELECT * FROM photo WHERE product_id = ?;";
    private static final String SQL__FIND_PHOTO_BY_NAME = "SELECT * FROM photo WHERE name = ?;";
    private static final String SQL__DELETE_PHOTO_BY_ID = "DELETE FROM photo WHERE id = ?;";
    private static final String SQL__CREATE_PHOTO = "INSERT INTO photo(product_id, name) VALUES (?, ?);";
    private static final String SQL__FIND_NEXT_ID =  "SELECT nextval(pg_get_serial_sequence('photo', 'id')) AS new_id;";


    public static List<Photo> getPhotosById(int id) throws DBException {
        List<Photo> photos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBManager.getInstance().getConnection();
            PhotoMapper mapper = new PhotoMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_PHOTOS_BY_PRODUCT_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                photos.add(mapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement, resultSet);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement, resultSet);
        }
        return photos;
    }


    public static Photo getPhotoByName(String name) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Photo photo = null;
        try{
            connection = DBManager.getInstance().getConnection();
            PhotoMapper mapper = new PhotoMapper();
            preparedStatement = connection.prepareStatement(SQL__FIND_PHOTO_BY_NAME);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                photo = mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return photo;
    }

    public static int getNextId() throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id = -1;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__FIND_NEXT_ID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return id;
    }


    public static void deletePhotoById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__DELETE_PHOTO_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }

    public static void createPhoto(Photo photo) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL__CREATE_PHOTO);
            preparedStatement.execute();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection, preparedStatement);
            throw new DBException(e);
        } finally {
            DBManager.getInstance().commitAndClose(connection, preparedStatement);
        }
    }



    private static class PhotoMapper implements EntityMapper<Photo>{

        @Override
        public Photo mapRow(ResultSet rs) {
            try {
                Photo photo = new Photo();
                photo.setId(rs.getInt(Fields.ID));
                photo.setProductId(rs.getInt(Fields.PHOTO_PRODUCT_ID));
                photo.setName(rs.getString(Fields.PHOTO_NAME));
                return photo;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
