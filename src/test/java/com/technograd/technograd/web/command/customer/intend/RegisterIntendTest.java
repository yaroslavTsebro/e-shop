package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.IntendDAO;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RegisterIntendTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        IntendDAO intendDAO = Mockito.mock(IntendDAO.class);
        RegisterIntend underTest = new RegisterIntend(intendDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        User user = new User();
        user.setId(id);
        Intend intend = new Intend();
        intend.setId(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockRequest.getParameter("register_by_id")).thenReturn(String.valueOf(id));
        when(mockRequest.getParameter("address")).thenReturn(String.valueOf(id));
        when(intendDAO.findCartById(anyInt())).thenReturn(intend);
        doNothing().when(intendDAO).changeCartIntoIntend(anyString(), anyInt());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_MENU_COMMAND;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(intendDAO, times(1)).findCartById(anyInt());
        verify(intendDAO, times(1)).changeCartIntoIntend(anyString(), anyInt());
    }
}