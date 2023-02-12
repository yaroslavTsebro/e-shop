package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ViewCartPageTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        IntendDAO intendDAO = Mockito.mock(IntendDAO.class);
        ListIntendDAO listIntendDAO = Mockito.mock(ListIntendDAO.class);
        ViewCartPage underTest = new ViewCartPage(intendDAO, listIntendDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);
        User user = new User();
        user.setId(id);
        String errorMessage = null;
        Intend cart = null;

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(intendDAO.findCartById(anyInt())).thenReturn(cart);

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = Path.CART_PAGE;;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(intendDAO, times(1)).findCartById(anyInt());
    }
}