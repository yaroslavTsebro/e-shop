package com.technograd.technograd.web.listener;

import com.technograd.technograd.web.command.customer.CreateCategory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    public void contextDestroyed(ServletContextEvent event) {
        logger.debug("Servlet context destruction starts");
        logger.debug("Servlet context destruction finished");
    }

    public void contextInitialized(ServletContextEvent event) {
        logger.debug("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();

        logger.debug("Servlet context initialization finished");
    }


    private void initLog4J(ServletContext servletContext) {
        logger.debug("Log4J initialization started");
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.debug("Log4J initialization finished");
    }

    private void initCommandContainer() {
        logger.debug("Command container initialization started");

        try {
            Class.forName("com.technograd.technograd.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        logger.debug("Command container initialization finished");
    }
}
