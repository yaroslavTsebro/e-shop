package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.ListIntendDAO;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


class DeleteFromCartTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        ListIntendDAO listIntendDAO = Mockito.mock(ListIntendDAO.class);
        DeleteFromCart underTest = new DeleteFromCart(listIntendDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        User user = new User();
        user.setId(1);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockRequest.getParameter("delete_li_by_id")).thenReturn(String.valueOf(id));
        doNothing().when(listIntendDAO).deleteListIntendByIdInCart(anyInt(), anyInt());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_CART_COMMAND;
        Assertions.assertEquals(actualCommand, expectedCommand);
    }
}