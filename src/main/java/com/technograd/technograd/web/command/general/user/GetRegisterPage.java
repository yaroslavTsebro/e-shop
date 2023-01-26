package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GetRegisterPage extends Command {

    private static final long serialVersionUID = -7349744215727100796L;
    private static final Logger logger = LogManager.getLogger(GetRegisterPage.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        try{
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int id = user.getId();
            logger.trace("id ->" + id);
            return Path.MENU_PAGE;
        } catch (Exception e){
            return Path.REGISTER_PAGE;
        }
    }
}
