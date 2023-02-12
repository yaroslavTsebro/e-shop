package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.customer.intend.AddProductAsElementOfCart;
import com.technograd.technograd.web.exсeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetRegisterPageTest {

    @Test
    void execute() throws ServletException, AppException, IOException {
        GetRegisterPage underTest = new GetRegisterPage();
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

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand =  mockRequest.getContextPath() + Commands.VIEW_LOGIN_PAGE;
        Assertions.assertEquals(expectedCommand, actualCommand);
    }
}