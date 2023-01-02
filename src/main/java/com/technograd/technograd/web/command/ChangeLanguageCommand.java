package com.technograd.technograd.web.command;


import com.technograd.technograd.web.error.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ChangeLanguageCommand extends Command {

    private static final long serialVersionUID = 5604700245317955539L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();

        String queryString = request.getParameter("queryString");
        String language = request.getParameter("language");

        session.setAttribute("lang", language);
        String forward = request.getContextPath() + request.getServletPath() + "?" + queryString;

        return forward;
    }
}
