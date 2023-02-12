package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.Product;
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
import static org.mockito.Mockito.times;

class UpdateProductCountInCartTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        ProductDAO productDAO = Mockito.mock(ProductDAO.class);
        ListIntendDAO listIntendDAO = Mockito.mock(ListIntendDAO.class);
        UpdateProductCountInCart underTest = new UpdateProductCountInCart(productDAO, listIntendDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 2;
        String idStr = String.valueOf(id);
        User user = new User();
        user.setId(id);
        String errorMessage = null;
        Intend cart = new Intend();
        cart.setId(id);
        Product product = new Product();
        product.setCount(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockRequest.getParameter("update_by_id")).thenReturn(String.valueOf(id - 1));
        when(mockRequest.getParameter("updated_li_count")).thenReturn(String.valueOf(id - 1));
        when(mockRequest.getParameter("update_by_product_id")).thenReturn(String.valueOf(id - 1));
        when(mockRequest.getParameter("update_by_product_count")).thenReturn(String.valueOf(id));

        when(productDAO.getProductById(anyInt())).thenReturn(product);
        doNothing().when(listIntendDAO).updateCountInListIntendByIdInCart(anyInt(), anyInt(), anyInt());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_CART_COMMAND;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(productDAO, times(1)).getProductById(anyInt());
        verify(listIntendDAO, times(1))
                .updateCountInListIntendByIdInCart(anyInt(), anyInt(), anyInt());
    }
}