package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CompanyDAOTest {

    private CompanyDAO instance;
    private Company expectedCompany;

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
        instance = new CompanyDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int companyId = 1;
        String companyNameUa = "nameUa";
        String companyNameEn = "nameEn";

        int countryId = 1;
        String countryNameUa = "nameUa";
        String countryNameEn = "nameEn";

        Country country = new Country();
        country.setId(companyId);
        country.setNameEn(countryNameEn);
        country.setNameUa(countryNameUa);


        when(mockRS.getInt(Fields.ID)).thenReturn(companyId);
        when(mockRS.getString(Fields.NAME_UA)).thenReturn(companyNameUa);
        when(mockRS.getString(Fields.NAME_EN)).thenReturn(companyNameEn);
        when(mockRS.getInt(Fields.COUNTRY_ID)).thenReturn(countryId);

        expectedCompany = new Company();
        expectedCompany.setId(companyId);
        expectedCompany.setNameEn(companyNameEn);
        expectedCompany.setNameUa(companyNameUa);
        expectedCompany.setCountry(country);
    }

    @Test
    void updateCompany() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Company company = new Company();
            company.setId(expectedCompany.getId());
            company.setNameUa(expectedCompany.getNameUa());
            company.setNameEn(expectedCompany.getNameEn());
            company.setCountry(expectedCompany.getCountry());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.updateCompany(company);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeUpdate();
            verify(dbManager, times(1)).closeResources(any(), any());

        }
    }

    @Test
    void createCompany() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Company company = new Company();
            company.setId(expectedCompany.getId());
            company.setNameUa(expectedCompany.getNameUa());
            company.setNameEn(expectedCompany.getNameEn());
            company.setCountry(expectedCompany.getCountry());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createCompany(company);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void deleteCompanyById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int deleteId = expectedCompany.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            instance.deleteCompanyById(deleteId);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

}