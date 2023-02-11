package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.dao.entity.DBManager;
import com.technograd.technograd.dao.entity.Fields;
import com.technograd.technograd.dao.entity.Supplier;
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

class SupplierDAOTest {

    private SupplierDAO instance;
    private Supplier expectedSupplier;

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
        instance = new SupplierDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int id = 1;
        String name = "name";
        String phone = "phone";
        String email = "email";

        when(mockRS.getInt(Fields.ID)).thenReturn(id);
        when(mockRS.getString(Fields.SUPPLIER_NAME)).thenReturn(name);
        when(mockRS.getString(Fields.SUPPLIER_PHONE)).thenReturn(phone);
        when(mockRS.getString(Fields.SUPPLIER_EMAIL)).thenReturn(email);

        expectedSupplier = new Supplier();
        expectedSupplier.setId(id);
        expectedSupplier.setName(name);
        expectedSupplier.setEmail(email);
        expectedSupplier.setPhone(phone);
    }


    @Test
    void createSupplier() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Supplier supplier = new Supplier();
            supplier.setId(expectedSupplier.getId());
            supplier.setName(expectedSupplier.getName());
            supplier.setEmail(expectedSupplier.getEmail());
            supplier.setPhone(expectedSupplier.getPhone());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createSupplier(supplier);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void getSupplierById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedSupplier.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            Supplier actual = instance.getSupplierById(id);
            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(expectedSupplier, actual);
        }
    }

    @Test
    void getAllSuppliers() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            List<Supplier> actual = instance.getAllSuppliers();
            List<Supplier> expectedSuppliers = new ArrayList<>();
            expectedSuppliers.add(expectedSupplier);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(1, actual.size());
            Assertions.assertEquals(expectedSuppliers, actual);
        }
    }

    @Test
    void updateSupplier() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            Supplier supplier = new Supplier();
            supplier.setId(expectedSupplier.getId());
            supplier.setName(expectedSupplier.getName());
            supplier.setEmail(expectedSupplier.getEmail());
            supplier.setPhone(expectedSupplier.getPhone());

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.updateSupplier(supplier);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }
}