package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProductCharacteristicDAOTest {

    private ProductCharacteristicDAO instance;
    private ProductCharacteristic expectedProductCharacteristic;

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
        instance = new ProductCharacteristicDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int id = 1;
        int productId = 1;
        Compatibility compatibility = new Compatibility(1, new Category(), new Characteristic());
        String value = "value";
        when(mockRS.getInt(Fields.ID)).thenReturn(id);
        when(mockRS.getInt(Fields.PRODUCT_CHARACTERISTIC_PRODUCT_ID)).thenReturn(productId);
        when(mockRS.getInt(Fields.PRODUCT_CHARACTERISTIC_COMPATIBILITY_ID)).thenReturn(compatibility.getId());
        when(mockRS.getString(Fields.PRODUCT_CHARACTERISTIC_VALUE)).thenReturn(value);

        expectedProductCharacteristic = new ProductCharacteristic();
        expectedProductCharacteristic.setId(id);
        expectedProductCharacteristic.setProductId(productId);
        expectedProductCharacteristic.setCompatibility(compatibility);
        expectedProductCharacteristic.setValue(value);
    }

    @Test
    void deleteById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int deleteId = expectedProductCharacteristic.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            instance.deleteById(deleteId);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }


    @Test
    void createProductCharacteristic() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            ProductCharacteristic pc = new ProductCharacteristic();
            pc.setId(expectedProductCharacteristic.getId());
            pc.setProductId(expectedProductCharacteristic.getProductId());
            pc.setCompatibility(expectedProductCharacteristic.getCompatibility());
            pc.setValue(expectedProductCharacteristic.getValue());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createProductCharacteristic(pc);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}