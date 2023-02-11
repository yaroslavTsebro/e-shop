package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import org.junit.jupiter.api.AfterEach;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CharacteristicDAOTest {

    private CharacteristicDAO instance;
    private Characteristic expectedCharacteristic;

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
        instance = new CharacteristicDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int characteristicId = 1;
        String nameUa = "nameUa";
        String nameEn = "nameEn";
        when(mockRS.getInt(Fields.ID)).thenReturn(characteristicId);
        when(mockRS.getString(Fields.NAME_UA)).thenReturn(nameUa);
        when(mockRS.getString(Fields.NAME_EN)).thenReturn(nameEn);

        expectedCharacteristic = new Characteristic();
        expectedCharacteristic.setId(characteristicId);
        expectedCharacteristic.setNameUa(nameUa);
        expectedCharacteristic.setNameEn(nameEn);
    }


    @Test
    void updateCharacteristic() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Characteristic characteristic = new Characteristic();
            characteristic.setId(expectedCharacteristic.getId());
            characteristic.setNameUa(expectedCharacteristic.getNameUa());
            characteristic.setNameEn(expectedCharacteristic.getNameEn());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.updateCharacteristic(characteristic);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeUpdate();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void searchCharacteristic() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            List<Characteristic> actualCharacteristics = instance.searchCharacteristic("nameUa");
            List<Characteristic> expectedCharacteristics = new ArrayList<>();
            expectedCharacteristics.add(expectedCharacteristic);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actualCharacteristics.size());
            Assertions.assertEquals(expectedCharacteristics, actualCharacteristics);
        }
    }

    @Test
    void getAllCharacteristics() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            List<Characteristic> actualCharacteristics = instance.getAllCharacteristics();
            List<Characteristic> expectedCharacteristics = new ArrayList<>();
            expectedCharacteristics.add(expectedCharacteristic);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actualCharacteristics.size());
            Assertions.assertEquals(expectedCharacteristics, actualCharacteristics);
        }
    }

    @Test
    void getCharacteristicById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedCharacteristic.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            Characteristic actual = instance.getCharacteristicById(id);
            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(expectedCharacteristic, actual);
        }
    }

    @Test
    void createCharacteristic() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Characteristic characteristic = new Characteristic();
            characteristic.setId(expectedCharacteristic.getId());
            characteristic.setNameEn(expectedCharacteristic.getNameEn());
            characteristic.setNameUa(expectedCharacteristic.getNameUa());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createCharacteristic(characteristic);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void deleteCharacteristicById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int deleteId = expectedCharacteristic.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            instance.deleteCharacteristicById(deleteId);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}