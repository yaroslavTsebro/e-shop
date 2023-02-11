package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.dao.entity.Country;
import com.technograd.technograd.dao.entity.DBManager;
import com.technograd.technograd.dao.entity.Fields;
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


class CountryDAOTest {

    private CountryDAO instance;
    private Country expectedCountry;

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
        instance = new CountryDAO();

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

        expectedCountry = new Country();
        expectedCountry.setId(characteristicId);
        expectedCountry.setNameUa(nameUa);
        expectedCountry.setNameEn(nameEn);
    }

    @Test
    void getAllCountries() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            List<Country> actualCharacteristics = instance.getAllCountries();
            List<Country> expectedCharacteristics = new ArrayList<>();
            expectedCharacteristics.add(expectedCountry);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actualCharacteristics.size());
            Assertions.assertEquals(expectedCharacteristics, actualCharacteristics);
        }
    }

    @Test
    void getCountryById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedCountry.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            instance.getCountryById(id);
            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(1)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());
        }
    }

    @Test
    void updateCountry() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Country country = new Country();
            country.setId(expectedCountry.getId());
            country.setNameUa(expectedCountry.getNameUa());
            country.setNameEn(expectedCountry.getNameEn());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.updateCountry(country);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeUpdate();
            verify(dbManager, times(1)).closeResources(any(), any());

        }
    }

    @Test
    void createCountry() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Country country = new Country();
            country.setId(expectedCountry.getId());
            country.setNameEn(expectedCountry.getNameEn());
            country.setNameUa(expectedCountry.getNameUa());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createCountry(country);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}