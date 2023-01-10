package com.technograd.technograd.web.command;

import com.technograd.technograd.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class NoCommand extends Command{
    private static final long serialVersionUID = -1244523817302849368L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String errorMessage;

        Object o = session.getAttribute("errorMessage");
        if (o != null) {
            errorMessage = o.toString();
        } else {
            errorMessage = "Something went wrong";
        }

        request.setAttribute("errorMessage", errorMessage);
        return Path.ERROR_PAGE;
    }
}
