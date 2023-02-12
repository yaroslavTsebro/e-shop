package com.technograd.technograd.web.command.manager.category;

import com.technograd.technograd.dao.CategoryDAO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UpdateCategoryTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        CategoryDAO categoryDAO = Mockito.mock(CategoryDAO.class);
        UpdateCategory underTest = new UpdateCategory(categoryDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getParameter("update_by_id")).thenReturn(idStr);
        when(mockRequest.getParameter("updated_name_ua")).thenReturn(idStr);
        when(mockRequest.getParameter("updated_name_en")).thenReturn(idStr);
        doNothing().when(categoryDAO).updateCategory(any());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        assertNull(mockSession.getAttribute("errorMessage"));
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_CATEGORIES;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(categoryDAO, times(1)).updateCategory(any());
    }
}