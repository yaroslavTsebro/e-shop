package com.technograd.technograd.web.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;

public class ContextListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent event) {

    }

    public void contextInitialized(ServletContextEvent event) {

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();
    }

    /**
     * Initializes log4j framework.
     *
     */
    private void initLog4J(ServletContext servletContext) {
        try {
            PropertyConfigurator.configure(servletContext.getRealPath(
                    "WEB-INF/classes/log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Initializes CommandContainer.
     *
     */
    private void initCommandContainer() {
        try {
            Class.forName("com.technograd.technograd.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
