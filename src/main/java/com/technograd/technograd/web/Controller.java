package com.technograd.technograd.web;

import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.CommandContainer;
import com.technograd.technograd.web.error.AppException;
import com.technograd.technograd.web.localization.LocalizationUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ResourceBundle;


public class Controller extends HttpServlet {
    private static final long serialVersionUID = 8239133597902771792L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
    private void process(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException{
        HttpSession session = request.getSession();
        ResourceBundle userRb = LocalizationUtils.getCurrentRb(session);
        ResourceBundle enRb = LocalizationUtils.getEnglishRb();

        String commandName = request.getParameter("command");
        Command command = CommandContainer.get(commandName);
        String forward = Commands.ERROR_PAGE_COMMAND;

        try{
            forward = command.execute(request, response);
        } catch (AppException exception) {
            session.setAttribute("errorMessage", userRb.getString(exception.getMessage()));
        }

        if(request.getMethod().equals("GET")){
            if(forward != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect(forward);
        }
    }
}
