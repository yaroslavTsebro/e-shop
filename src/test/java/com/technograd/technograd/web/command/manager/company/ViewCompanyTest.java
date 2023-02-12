package com.technograd.technograd.web.command.manager.company;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.CountryDAO;
import com.technograd.technograd.dao.entity.Company;
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

class ViewCompanyTest {

    @Test
    void execute() throws ServletException, AppException, IOException, DBException {
        CompanyDAO companyDAO = Mockito.mock(CompanyDAO.class);
        CountryDAO countryDAO = Mockito.mock(CountryDAO.class);
        ViewCompany underTest = new ViewCompany(companyDAO, countryDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(companyDAO.getAllCompanies()).thenReturn(new ArrayList<Company>());
        when(companyDAO.getAllCompanies()).thenReturn(new ArrayList<Company>());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = Path.COMPANY_PAGE;
        assertNull(mockSession.getAttribute("errorMessage"));
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(companyDAO, times(1)).getAllCompanies();
    }
}