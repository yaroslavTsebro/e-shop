package com.technograd.technograd.web.command.customer;

import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Product;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ChangeCountOfProductInCart extends Command {

    private static final long serialVersionUID = 1966444134659268694L;
    private static final Logger logger = LogManager.getLogger(ChangeCountOfProductInCart.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ChangeCountOfProductInCart execute started");

        int id = Integer.parseInt(request.getParameter("element_of_cart_id"));
        logger.trace("element_of_cart_id ->" + id);
        int count = Integer.parseInt(request.getParameter("count"));
        logger.trace("count ->" + count);

        int productCount;
        try {
            productCount = ProductDAO.getProductById(id).getCount();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if(count > productCount){
            throw new AppException("Count more then current count");
        }

        if(count < 0){
            throw new AppException("error");
        } else if(count == 0){
            try {
                ListIntendDAO.deleteListIntendById(id);
                logger.trace("listIntend with this id was deleted:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        } else{
            try {
                ListIntendDAO.updateCountInListIntendById(id, count);
                logger.trace("listIntend with this id was updated:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("ChangeCountOfProductInCart execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCart";
    }
}
