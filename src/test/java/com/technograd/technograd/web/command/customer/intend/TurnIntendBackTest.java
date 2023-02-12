package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.entity.Condition;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


class TurnIntendBackTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        IntendDAO intendDAO = Mockito.mock(IntendDAO.class);
        ListIntendDAO listIntendDAO = Mockito.mock(ListIntendDAO.class);
        TurnIntendBack underTest = new TurnIntendBack(intendDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String query = "UPDATE intend SET condition='TURNED_BACK' , end_date = current_timestamp WHERE id=?;";
        String idStr = String.valueOf(id);
        User user = new User();
        user.setId(id);
        Intend intend = new Intend();
        intend.setId(id);
        intend.setUserId(1);
        intend.setCondition(Condition.NEW);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockRequest.getParameter("intend_id")).thenReturn(idStr);
        when(mockRequest.getParameter("reason")).thenReturn(idStr);
        when(intendDAO.findIntendById(anyInt())).thenReturn(intend);
        when(intendDAO.buildUpdateConditionQuery(any())).thenReturn(query);
        doNothing().when(intendDAO)
                .updateConditionTurnedBackFromUserWithReason(anyString(), anyInt(), anyString());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_CURRENT_INTEND + id + "";
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(intendDAO, times(1)).findIntendById(anyInt());
        verify(intendDAO, times(1)).buildUpdateConditionQuery(any());
        verify(intendDAO, times(1)).updateConditionTurnedBackFromUser(anyString(), anyInt(), anyString());
    }
}