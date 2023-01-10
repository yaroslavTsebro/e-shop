package com.technograd.technograd.web.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ContextListener implements ServletContextListener {
    Logger logger = Logger.getLogger(ContextListener.class.getName());

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
            PropertyConfigurator.configure(servletContext.getRealPath(
                    "WEB-INF/classes/log4j.properties"));
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
