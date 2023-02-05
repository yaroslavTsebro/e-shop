package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.Path;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GetSendCodePage extends Command {

    private static final long serialVersionUID = 8648453259921631935L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        return Path.SEND_LINK_PAGE;
    }
}
