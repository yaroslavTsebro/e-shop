package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class IntendDAOTest {

    private IntendDAO instance;
    private Intend expectedIntend;

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
        instance = new IntendDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int id = 1;
        Timestamp startDate = new Timestamp((long) 1000000);
        Timestamp endDate = new Timestamp((long) 1000000);
        SendingOrReceiving sendingOrReceiving = SendingOrReceiving.SENDING;
        String address = "address";
        Condition condition = Condition.COMPLETED;

        when(mockRS.getInt(Fields.ID)).thenReturn(id);
        when(mockRS.getTimestamp(Fields.INTEND_START_DATE)).thenReturn(startDate);
        when(mockRS.getTimestamp(Fields.INTEND_END_DATE)).thenReturn(endDate);
        when(mockRS.getInt(Fields.INTEND_USER_ID)).thenReturn(id);
        when(mockRS.getInt(Fields.INTEND_SUPPLIER_ID)).thenReturn(id);
        when(mockRS.getInt(Fields.INTEND_EMPLOYEE_ID)).thenReturn(id);
        when(mockRS.getString(Fields.INTEND_SENDING_OR_RECEIVING)).thenReturn(sendingOrReceiving.toString());
        when(mockRS.getString(Fields.INTEND_ADDRESS)).thenReturn(address);
        when(mockRS.getString(Fields.INTEND_CONDITION)).thenReturn(condition.toString());


        expectedIntend = new Intend();
        expectedIntend.setId(id);
        expectedIntend.setStartDate(startDate);
        expectedIntend.setEndDate(endDate);
        expectedIntend.setUserId(id);
        expectedIntend.setSupplierId(id);
        expectedIntend.setEmployeeId(id);
        expectedIntend.setSendingOrReceiving(sendingOrReceiving);
        expectedIntend.setAddress(address);
        expectedIntend.setCondition(condition);
    }

    @Test
    void deleteCartAndListIntendById() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int deleteId = expectedIntend.getId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            instance.deleteCartAndListIntendById(deleteId);

            verify(mockCon, times(2)).prepareStatement(anyString());
            verify(mockPS, times(2)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void createIntendSending() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.createIntendSending(1);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

    @Test
    void changeCartIntoIntend() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try(MockedStatic<DBManager> ignored = mockStatic(DBManager.class)){
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedIntend.getId();
            String address = expectedIntend.getAddress();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());
            doNothing().when(mockPS).setString(anyInt(), anyString());

            instance.changeCartIntoIntend(address, id);

            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).execute();
            verify(dbManager, times(1)).commitAndClose(any(), any());
        }
    }

}