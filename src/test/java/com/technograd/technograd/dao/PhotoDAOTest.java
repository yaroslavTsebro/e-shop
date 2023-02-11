package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.dao.entity.DBManager;
import com.technograd.technograd.dao.entity.Fields;
import com.technograd.technograd.dao.entity.Photo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PhotoDAOTest {

    private PhotoDAO instance;
    private Photo expectedPhoto;

    @Mock
    Connection mockCon;
    @Mock
    PreparedStatement mockPS;
    @Mock
    ResultSet mockRS;
    @BeforeEach
    void setUp() throws SQLException {
        mockCon = Mockito.mock(Connection.class);
        mockPS = Mockito.mock(PreparedStatement.class);
        mockRS = Mockito.mock(ResultSet.class);
        instance = new PhotoDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int id = 1;
        String name = "nameUa";

        when(mockRS.getInt(Fields.ID)).thenReturn(id);
        when(mockRS.getString(Fields.PHOTO_NAME)).thenReturn(name);
        when(mockRS.getInt(Fields.PHOTO_PRODUCT_ID)).thenReturn(id);

        expectedPhoto = new Photo();
        expectedPhoto.setId(id);
        expectedPhoto.setName(name);
        expectedPhoto.setProductId(id);
    }

    @Test
    void insertListOfPhotos() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)) {
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            List<Photo> photos = new ArrayList<>();
            Photo photo = new Photo();
            photo.setId(expectedPhoto.getId());
            photo.setName(expectedPhoto.getName());
            photo.setProductId(expectedPhoto.getProductId());
            photos.add(photo);

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.insertListOfPhotos(photos);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void getFirstPhotoByProductId() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedPhoto.getId();
            List<Photo> actualPhotos = instance.getPhotosById(id);
            List<Photo> expectedPhotos = new ArrayList<>();
            expectedPhotos.add(expectedPhoto);


            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actualPhotos.size());
            Assertions.assertEquals(expectedPhotos, actualPhotos);
        }
    }

    @Test
    void getPhotosById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedPhoto.getId();
            List<Photo> actualPhotos = instance.getPhotosById(id);
            List<Photo> expectedPhotos = new ArrayList<>();
            expectedPhotos.add(expectedPhoto);


            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actualPhotos.size());
            Assertions.assertEquals(expectedPhotos, actualPhotos);
        }
    }

    @Test
    void deletePhotoById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            String actualName = instance.deletePhotoById(expectedPhoto.getId());
            String expectedName =expectedPhoto.getName();

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(expectedName, actualName);
        }
    }

    @Test
    void createPhoto() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Photo photo = new Photo();
            photo.setId(expectedPhoto.getId());
            photo.setName(expectedPhoto.getName());
            photo.setProductId(expectedPhoto.getProductId());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createPhoto(photo);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}