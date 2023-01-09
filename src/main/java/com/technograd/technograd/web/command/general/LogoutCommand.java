package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.web.error.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutCommand {

    private static final long serialVersionUID = 2738928078046577631L;

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return Path.MENU_PAGE;
    }
}
