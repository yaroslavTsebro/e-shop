package com.technograd.technograd.web.command.manager.characteristic;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CharacteristicDAO;
import com.technograd.technograd.dao.entity.Characteristic;
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

class SearchCharacteristicTest {

    @Test
    void execute() throws DBException, ServletException, AppException, IOException {
        CharacteristicDAO characteristicDAO = Mockito.mock(CharacteristicDAO.class);
        SearchCharacteristic underTest = new SearchCharacteristic(characteristicDAO);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession mockSession = Mockito.mock(HttpSession.class);

        int id = 1;
        String idStr = String.valueOf(id);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getParameter("pattern")).thenReturn(idStr);
        when(characteristicDAO.searchCharacteristic(anyString())).thenReturn(new ArrayList<Characteristic>());

        String actualCommand = underTest.execute(mockRequest, mockResponse);
        String expectedCommand = Path.CHARACTERISTIC_PAGE;
        assertNull(mockSession.getAttribute("errorMessage"));
        Assertions.assertEquals(expectedCommand, actualCommand);

        verify(characteristicDAO, times(1)).searchCharacteristic(anyString());
    }
}