package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.*;
import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.customer.intend.AddProductAsElementOfCart;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ViewProductCommandTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        ProductDAO productDAO = Mockito.mock(ProductDAO.class);
        CategoryDAO categoryDAO = Mockito.mock(CategoryDAO.class);
        CompanyDAO companyDAO = Mockito.mock(CompanyDAO.class);
        ViewProductCommand underTest = new ViewProductCommand(productDAO, categoryDAO, companyDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);

        int id = 1;
        String idStr = String.valueOf(id);


        when(mockRequest.getParameter("id")).thenReturn(idStr);

        when(productDAO.getProductById(anyInt())).thenReturn(new Product());
        when(categoryDAO.getAllCategories()).thenReturn(new ArrayList<Category>());
        when(companyDAO.getAllCompanies()).thenReturn(new ArrayList<Company>());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = Path.VIEW_PRODUCT_PAGE;
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(productDAO, times(1)).getProductById(anyInt());
        verify(categoryDAO, times(1)).getAllCategories();
        verify(companyDAO, times(1)).getAllCompanies();
    }
}