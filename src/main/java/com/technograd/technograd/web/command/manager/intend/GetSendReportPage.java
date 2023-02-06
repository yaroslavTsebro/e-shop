package com.technograd.technograd.web.command.manager.intend;

import com.technograd.technograd.Path;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class GetSendReportPage extends Command {

    private static final long serialVersionUID = -8678494524533674507L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        return Path.REPORT_PAGE;
    }
}
