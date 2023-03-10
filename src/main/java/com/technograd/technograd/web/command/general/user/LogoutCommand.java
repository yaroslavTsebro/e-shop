package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutCommand extends Command {

    private static final long serialVersionUID = 2738928078046577631L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return request.getContextPath() + "/controller?command=viewMenu";
    }
}
