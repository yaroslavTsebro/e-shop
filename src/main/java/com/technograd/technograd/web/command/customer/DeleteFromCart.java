package com.technograd.technograd.web.command.customer;

import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serial;

public class DeleteFromCart extends Command {

    private static final long serialVersionUID = 5424654322972585328L;
    private static final Logger logger = LogManager.getLogger(DeleteFromCart.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("DeleteFromCart execute started");

        int id = Integer.parseInt(request.getParameter("element_of_cart_id"));
        logger.trace("element_of_cart_id ->" + id);

        try {
            ListIntendDAO.deleteListIntendById(id);
            logger.trace("listIntend with this id was deleted:" + id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        logger.info("DeleteFromCart execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCart";
    }
}
