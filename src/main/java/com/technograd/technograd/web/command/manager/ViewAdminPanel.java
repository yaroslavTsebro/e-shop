package com.technograd.technograd.web.command.manager;

import com.technograd.technograd.Path;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ViewAdminPanel extends Command {
    private static final long serialVersionUID = 2080535881887356554L;
    private static final Logger logger = LogManager.getLogger(ViewAdminPanel.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewAdminPanel execute started");
        logger.info("ViewAdminPanel execute finished, path transferred to controller");
        return Path.ADMIN_PANEL;
    }
}
