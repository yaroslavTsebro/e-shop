package com.technograd.technograd.web.command;

import com.technograd.technograd.Path;
import com.technograd.technograd.web.error.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class NoCommand extends Command{

    private static final long serialVersionUID = 158927630931195004L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        String errorMessage;
        Object obj = session.getAttribute("errorMessage");
        if(obj != null){
            errorMessage = obj.toString();
        } else {
            errorMessage = "Something went wrong";
        }

        request.setAttribute("errorMessage", errorMessage);
        return Path.ERROR_PAGE;
    }
}
