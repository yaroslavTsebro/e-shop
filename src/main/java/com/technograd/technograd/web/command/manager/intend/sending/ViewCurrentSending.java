package com.technograd.technograd.web.command.manager.intend.sending;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ViewCurrentSending extends Command {
    private static final long serialVersionUID = 8591695459490297254L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        int id = Integer.parseInt(request.getParameter("id"));
        Intend intend;
        User user;
        try{
            intend = IntendDAO.findIntendById(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        try{
            user = UserDAO.getReducedUserById(intend.getUserId());
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("intend", intend);
        request.setAttribute("user", user);
        return Path.ADMIN_CURRENT_SENDING_PAGE;
    }
}
