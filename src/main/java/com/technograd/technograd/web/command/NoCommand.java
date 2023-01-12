package com.technograd.technograd.web.command;

import com.technograd.technograd.Path;
import com.technograd.technograd.web.command.customer.CreateCategory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class NoCommand extends Command{
    private static final long serialVersionUID = -1244523817302849368L;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("NoCommand is started");

        HttpSession session = request.getSession();
        String errorMessage;

        Object o = session.getAttribute("errorMessage");
        if (o != null) {
            errorMessage = o.toString();
        } else {
            errorMessage = "Something went wrong";
        }

        request.setAttribute("errorMessage", errorMessage);
        logger.error("Set the request attribute: errorMessage -> " + errorMessage);
        logger.debug("NoCommand is finished");
        return Path.ERROR_PAGE;
    }
}
