package com.technograd.technograd.web.filter;

import com.technograd.technograd.web.command.customer.CreateCategory;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encoding;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    @Override
    public void init(FilterConfig filterConfig) {
       logger.info("Encoding filter initialization is started");

        encoding = filterConfig.getInitParameter("encoding");
       logger.trace("Encoding from web.xml ==> " + encoding);

       logger.info("Encoding filter initialization is finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Encoding filter starts");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        logger.trace("Request uri ==> " + httpServletRequest.getRequestURI());

        String requestEncoding = servletRequest.getCharacterEncoding();
        if (requestEncoding == null) {
            logger.trace("Request encoding is null, set encoding ==> " + encoding);
            servletRequest.setCharacterEncoding(encoding);
        }

        logger.info("Encoding filter is finished");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
       logger.debug("Encoding filter destroying");
    }
}
