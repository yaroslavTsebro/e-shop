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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CompatibilityDAOTest {

    private CompatibilityDAO instance;
    private Compatibility expectedCompatibility;

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
        instance = new CompatibilityDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int id = 1;
        Category category = new Category();
        category.setId(id);
        Characteristic characteristic = new Characteristic();
        characteristic.setId(id);

        when(mockRS.getInt(Fields.ID)).thenReturn(id);
        when(mockRS.getInt(Fields.COMPATIBILITY_CATEGORY_ID)).thenReturn(id);
        when(mockRS.getInt(Fields.COMPATIBILITY_CHARACTERISTIC_ID)).thenReturn(id);


        expectedCompatibility = new Compatibility();
        expectedCompatibility.setId(id);
        expectedCompatibility.setCharacteristic(characteristic);
        expectedCompatibility.setCategory(category);
    }


    @Test
    void createCompatibility() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Compatibility compatibility = new Compatibility();
            compatibility.setId(expectedCompatibility.getId());
            compatibility.setCharacteristic(expectedCompatibility.getCharacteristic());
            compatibility.setCategory(expectedCompatibility.getCategory());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createCompatibility(compatibility);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}