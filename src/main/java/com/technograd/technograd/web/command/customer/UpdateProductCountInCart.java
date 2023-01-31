package com.technograd.technograd.web.command.customer;

import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class UpdateProductCountInCart extends Command {

    private static final long serialVersionUID = 1966444134659268694L;
    private static final Logger logger = LogManager.getLogger(UpdateProductCountInCart.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("UpdateProductCountInCart execute started");

        int id = Integer.parseInt(request.getParameter("update_by_id"));
        logger.trace("update_by_id ->" + id);
        int newCount = Integer.parseInt(request.getParameter("updated_li_count"));
        logger.trace("updated_li_count ->" + newCount);

        int productId = Integer.parseInt(request.getParameter("update_by_product_id"));
        logger.trace("update_by_product_id ->" + productId);
        int count = Integer.parseInt(request.getParameter("update_by_product_count"));
        logger.trace("update_by_product_count ->" + count);

        if(count == newCount){
            return request.getContextPath() + "/controller?command=viewCart";
        }

        int productCount;
        try {
            productCount = ProductDAO.getProductById(productId).getCount();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if(count > productCount){
            throw new AppException("Count more then current count");
        }

        if(newCount <= 0){
            try {
                ListIntendDAO.deleteListIntendById(id);
                logger.trace("listIntend with this id was deleted:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        } else{
            try {
                ListIntendDAO.updateCountInListIntendById(id, newCount);
                logger.trace("listIntend with this id was updated:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("UpdateProductCountInCart execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCart";
    }
}
