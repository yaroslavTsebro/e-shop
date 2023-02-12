package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.dao.CompanyDAO;
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

class UpdateCompanyTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        CompanyDAO companyDAO = Mockito.mock(CompanyDAO.class);
        UpdateCompany underTest = new UpdateCompany(companyDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getParameter("update_by_id")).thenReturn(idStr);
        when(mockRequest.getParameter("updated_name_ua")).thenReturn(idStr);
        when(mockRequest.getParameter("updated_name_en")).thenReturn(idStr);
        when(mockRequest.getParameter("updated_country_id")).thenReturn(idStr);
        doNothing().when(companyDAO).updateCompany(any());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = mockRequest.getContextPath() + Commands.VIEW_COMPANIES;
        assertNull(mockSession.getAttribute("errorMessage"));
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(companyDAO, times(1)).updateCompany(any());
    }
}