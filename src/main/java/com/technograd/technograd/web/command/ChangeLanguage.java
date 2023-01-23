package com.technograd.technograd.web.command;

import com.technograd.technograd.web.exeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class ChangeLanguage extends Command {

    private static final long serialVersionUID = 5604700245317955539L;
    private static final Logger logger = LogManager.getLogger(ChangeLanguage.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("Change language command is started");

        HttpSession session = request.getSession();

        String queryString = request.getParameter("queryString");
        String language = request.getParameter("language");

        session.setAttribute("lang", language);
        logger.trace("Set session attribute lang =>" + language);

        String forward = request.getContextPath() + request.getServletPath() + "?" + queryString;
        logger.debug("Change language command is finished");
        return forward;
    }
}
