package com.technograd.technograd.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
       // logger.debug("Encoding filter initialization is started");

        encoding = filterConfig.getInitParameter("encoding");
       // logger.trace("Encoding from web.xml ==> " + encoding);

       // logger.debug("Encoding filter initialization is finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      //  logger.debug("Encoding filter starts");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
       // logger.trace("Request uri ==> " + httpServletRequest.getRequestURI());

        String requestEncoding = servletRequest.getCharacterEncoding();
        if (requestEncoding == null) {
          //  logger.trace("Request encoding is null, set encoding ==> " + encoding);
            servletRequest.setCharacterEncoding(encoding);
        }

       // logger.debug("Encoding filter is finished");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
       // logger.debug("Encoding filter destroying");
    }
}
