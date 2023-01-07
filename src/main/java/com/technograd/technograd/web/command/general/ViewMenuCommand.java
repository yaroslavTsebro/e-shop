package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.error.AppException;
import com.technograd.technograd.web.error.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class ViewMenuCommand extends Command {

    private static final long serialVersionUID = -1227114065336794942L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        return Path.MENU_PAGE;
    }

}
