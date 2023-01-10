package com.technograd.technograd.web;

import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.CommandContainer;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.localization.LocalizationUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet(name = "controller", value = "/")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 8239133597902771792L;
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Controller started");

        HttpSession session = request.getSession();
        ResourceBundle userRb = LocalizationUtils.getCurrentRb(session);
        ResourceBundle enRb = LocalizationUtils.getEnglishRb();

        String uri = request.getRequestURI();
        String query = request.getQueryString();
        System.out.println(uri);
        Command command;
        if(query == null || query.length() == 0){
            command = CommandContainer.get(uri);
            logger.trace("Request parameter without query: command -> " + command);
        } else{
            command = CommandContainer.get(uri + "?" + query);
            logger.trace("Request parameter with query: command -> " + command);
        }
        String forward = Commands.ERROR_PAGE_COMMAND;
        try {
            forward = command.execute(request, response);
        } catch (AppException exception) {
            session.setAttribute("errorMessage", userRb.getString(exception.getMessage()));
        }

        logger.trace("Forward address -> " + forward);

        logger.info("Controller finished, forward to address -> " + forward);

        if (request.getMethod().equals("GET")) {
            if (forward != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect(forward);
        }
    }
}
