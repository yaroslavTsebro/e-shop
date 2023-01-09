package com.technograd.technograd.web.command;


import com.technograd.technograd.web.error.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ChangeLanguagePage", value = "/chdfa")
public class ChangeLanguageServlet extends HttpServlet {

    private static final long serialVersionUID = 5604700245317955539L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String localeName = "en";
        String queryString = request.getParameter("queryString");
        String localeObj = (String)session.getAttribute("lang");
        if (localeObj != null) {
            localeName = localeObj.toString();
        }
        if(localeName.equals("en")){

        }

        String forward = request.getContextPath() + request.getServletPath() + "?" + queryString;

        response.sendRedirect(request.getServletPath());
    }
}
