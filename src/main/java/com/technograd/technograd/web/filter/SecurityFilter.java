package com.technograd.technograd.web.filter;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.entity.Post;
import com.technograd.technograd.web.command.manager.category.CreateCategory;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.relation.Role;
import java.io.IOException;
import java.util.*;

public class SecurityFilter implements Filter {
    private static final Map<Post, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    @Override
    public void init(FilterConfig config) {
        logger.info("Security filter initialization is started");

        accessMap.put(Post.MANAGER , asList(config.getInitParameter("manager")));
        accessMap.put(Post.CUSTOMER, asList(config.getInitParameter("customer")));
        logger.trace("Access map --> " + accessMap);


        outOfControl = asList(config.getInitParameter("out-of-control"));
        logger.trace("Out of control commands --> " + outOfControl);

        logger.info("Security filter initialization is finished");
    }

    private List<String> asList(String str) {
        List<String> result = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info("Security filter is started");
        if (accessAllowed(req)) {
            logger.debug("Filter is finished");
            chain.doFilter(req, res);
        } else {
            String errorMessage = "You do not have access to the chosen page";
            req.setAttribute("errorMessage", errorMessage);
            logger.trace("Set the request attribute: error message -> " + errorMessage);
            req.getRequestDispatcher(Path.ERROR_PAGE).forward(req, res);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        logger.trace("COMMAND NAME = " + commandName);
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpServletRequest.getSession(false);
        logger.trace("SESSION = " + session);
        if (session == null) {
            return false;
        }

        Post userPost = (Post) session.getAttribute("userPost");
        logger.trace("USER POST =" + userPost);
        if (userPost == null) {
            return false;
        }
        return accessMap.get(userPost).contains(commandName);
    }

    @Override
    public void destroy() {
       logger.debug("Security filter destruction");
    }
}
