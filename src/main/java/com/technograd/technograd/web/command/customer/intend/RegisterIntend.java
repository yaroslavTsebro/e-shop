package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
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

    private final IntendDAO intendDAO;

    public RegisterIntend(IntendDAO intendDAO) {
        this.intendDAO = intendDAO;
    }
    public RegisterIntend() {
        this.intendDAO = new IntendDAO();
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("RegisterIntend execute started");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        int cartId = Integer.parseInt(request.getParameter("register_by_id"));
        logger.trace("register_by_id ->" + cartId);

        String address = request.getParameter("address");
        logger.trace("address ->" + address);

        Intend intend = null;
        try{
            intend = intendDAO.findCartById(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if(intend.getId() != cartId){
            throw new RuntimeException();
        }

       if(intend != null){
           try {
               intendDAO.changeCartIntoIntend(address, cartId);
           } catch (DBException e) {
               throw new RuntimeException(e);
           }
       }
        logger.info("RegisterIntend execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewMenu";
    }
}
