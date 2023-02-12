package com.technograd.technograd.web.command.manager.category;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.entity.Category;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class SearchCategoryTest {

    @Test
    void execute() throws ServletException, AppException, IOException, DBException {
        CategoryDAO categoryDAO = Mockito.mock(CategoryDAO.class);
        SearchCategory underTest = new SearchCategory(categoryDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getParameter("pattern")).thenReturn(idStr);
        when(categoryDAO.searchCategories(anyString())).thenReturn(new ArrayList<Category>());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = Path.CATEGORY_PAGE;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(categoryDAO, times(1)).searchCategories(anyString());
    }
}