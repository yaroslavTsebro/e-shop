package com.technograd.technograd.web.command.customer.intend;

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
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AddProductAsElementOfCartTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        IntendDAO intendDAO = Mockito.mock(IntendDAO.class);
        ListIntendDAO listIntendDAO = Mockito.mock(ListIntendDAO.class);
        AddProductAsElementOfCart underTest = new AddProductAsElementOfCart(intendDAO, listIntendDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);
        User user = new User();
        user.setId(id);
        Intend cart = new Intend();
        cart.setId(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockRequest.getParameter("product_count")).thenReturn(idStr);
        when(mockRequest.getParameter("add_to_cart_count")).thenReturn(idStr);
        when(mockRequest.getParameter("product_id")).thenReturn(idStr);
        when(mockRequest.getParameter("product_price")).thenReturn(idStr);
        when(intendDAO.findCartById(anyInt())).thenReturn(cart);
        when(listIntendDAO.checkCartForProduct(anyInt(), anyInt(), anyInt())).thenReturn(null);
        doNothing().when(listIntendDAO)
                .createListIntend(anyInt(), anyInt(), anyInt(), any());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_CART_COMMAND;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(intendDAO, times(1)).findCartById(anyInt());
        verify(listIntendDAO, times(1)).checkCartForProduct(anyInt(), anyInt(), anyInt());
        verify(listIntendDAO, times(1)).createListIntend(anyInt(), anyInt(), anyInt(), any());
    }
}