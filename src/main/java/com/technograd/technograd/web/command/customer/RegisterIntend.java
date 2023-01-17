package com.technograd.technograd.web.command.customer;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RegisterIntend extends Command {

    private static final long serialVersionUID = 4941631989800207120L;
    private static final Logger logger = LogManager.getLogger(RegisterIntend.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("RegisterIntend execute started");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        String address = request.getParameter("address");
        logger.trace("address ->" + address);

        Intend intend = null;
        try{
            intend = IntendDAO.findCartById(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
       if(intend != null){
           try {
               IntendDAO.changeCartIntoIntend(address, id);
           } catch (DBException e) {
               throw new RuntimeException(e);
           }
       }
        logger.info("RegisterIntend execute finished, path transferred to controller");
        return Path.MENU_PAGE;
    }
}