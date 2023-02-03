package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SendConfirmationLinkCommand extends Command {

    private static final long serialVersionUID = -2227048688293446672L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        return null;
    }
}
