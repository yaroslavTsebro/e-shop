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


public class ViewCartPage extends Command {

    private static final long serialVersionUID = 2860258631707756273L;
    private static final Logger logger = LogManager.getLogger(ViewCartPage.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewCartPage execute started");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        Intend intend;
        try{
            intend = IntendDAO.findCartById(id);
            logger.trace("cart ->" + intend);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("cart", intend);
        logger.info("ViewCartPage execute finished, path transferred to controller");
        return Path.CART_PAGE;
    }
}
