package com.technograd.technograd.web.command;


import com.technograd.technograd.web.error.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {

    private static final long serialVersionUID = 3768398332522534932L;

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
