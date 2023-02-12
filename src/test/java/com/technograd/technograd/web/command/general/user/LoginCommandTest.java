package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.Post;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.customer.intend.AddProductAsElementOfCart;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import com.technograd.technograd.web.recaptcha.RecaptchaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LoginCommandTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        LoginCommand underTest = new LoginCommand(userDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);
        User user = new User();
        user.setId(id);
        user.setPassword(idStr);
        user.setLocaleName("en");
        user.setPost(Post.CUSTOMER);
        user.setSalt(idStr);
        Intend cart = new Intend();
        cart.setId(id);
        boolean valid = true;

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getParameter("email")).thenReturn(idStr);
        when(mockRequest.getParameter("password")).thenReturn(idStr);
        when(mockRequest.getParameter("g-recaptcha-response")).thenReturn("");


        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_LOGIN_PAGE;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(mockRequest, times(1)).getSession();
        verify(mockRequest, times(3)).getParameter(anyString());
    }
}