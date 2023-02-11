package com.technograd.technograd.dao;

import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.dao.entity.DBManager;
import com.technograd.technograd.dao.entity.Fields;
import com.technograd.technograd.dao.entity.IntendReturn;
import org.junit.jupiter.api.Assertions;
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

class IntendReturnDAOTest {

    private IntendReturnDAO instance;
    private IntendReturn expectedIntendReturn;

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
        instance = new IntendReturnDAO();

        doNothing().when(mockCon).commit();
        when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
        doNothing().when(mockPS).setInt(anyInt(), anyInt());
        when(mockPS.executeQuery()).thenReturn(mockRS);
        when(mockRS.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        int id = 1;
        int intendId = 1;
        Date date = new Date(2020, 12, 20);
        String reason = "reason";

        when(mockRS.getInt(Fields.ID)).thenReturn(id);
        when(mockRS.getInt(Fields.INTEND_RETURN_INTEND_ID)).thenReturn(intendId);
        when(mockRS.getDate(Fields.INTEND_RETURN_DATE)).thenReturn(date);
        when(mockRS.getString(Fields.INTEND_RETURN_REASON)).thenReturn(reason);

        expectedIntendReturn = new IntendReturn();
        expectedIntendReturn.setId(id);
        expectedIntendReturn.setIntendId(intendId);
        expectedIntendReturn.setDate(date);
        expectedIntendReturn.setReason(reason);
    }

    @Test
    void findIntendReturnByIntendId() throws SQLException {
        DBManager dbManager = Mockito.mock(DBManager.class);
        try (MockedStatic<DBManager> ignored = mockStatic(DBManager.class)) {
            when(DBManager.getInstance()).thenReturn(dbManager);
            when(DBManager.getInstance().getConnection()).thenReturn(mockCon);

            int id = expectedIntendReturn.getIntendId();

            when(mockCon.prepareStatement(anyString())).thenReturn(mockPS);
            doNothing().when(mockPS).setInt(anyInt(), anyInt());

            IntendReturn actual = instance.findIntendReturnByIntendId(id);
            verify(mockCon, times(1)).prepareStatement(anyString());
            verify(mockPS, times(1)).executeQuery();
            verify(mockRS, times(2)).next();
            verify(dbManager, times(1)).commitAndClose(any(), any(), any());

            Assertions.assertEquals(expectedIntendReturn, actual);
        }
    }
}