package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
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

public class UpdateProductCountInCart extends Command {

    private static final long serialVersionUID = 1966444134659268694L;
    private static final Logger logger = LogManager.getLogger(UpdateProductCountInCart.class.getName());
    private final ProductDAO productDAO;
    private final ListIntendDAO listIntendDAO;

    public UpdateProductCountInCart() {
        this.productDAO = new ProductDAO();
        this.listIntendDAO = new ListIntendDAO();
    }

    public UpdateProductCountInCart(ProductDAO productDAO, ListIntendDAO listIntendDAO) {
        this.productDAO = productDAO;
        this.listIntendDAO = listIntendDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("UpdateProductCountInCart execute started");


        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        logger.trace("userId ->" + userId);

        int id = Integer.parseInt(request.getParameter("update_by_id"));
        logger.trace("update_by_id ->" + id);
        int newCount = Integer.parseInt(request.getParameter("updated_li_count"));
        logger.trace("updated_li_count ->" + newCount);

        int productId = Integer.parseInt(request.getParameter("update_by_product_id"));
        logger.trace("update_by_product_id ->" + productId);
        int count = Integer.parseInt(request.getParameter("update_by_product_count"));
        logger.trace("update_by_product_count ->" + count);

        if(count == newCount){
            return request.getContextPath() + Commands.VIEW_CART_COMMAND;
        }

        int productCount;
        try {
            productCount = productDAO.getProductById(productId).getCount();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if(count > productCount){
            throw new AppException("Count more then current count");
        }

        if(newCount <= 0){
            try {
                listIntendDAO.deleteListIntendByIdInCart(id, userId);
                logger.trace("listIntend with this id was deleted:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        } else{
            try {
                listIntendDAO.updateCountInListIntendByIdInCart(id, newCount, userId);
                logger.trace("listIntend with this id was updated:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("UpdateProductCountInCart execute finished, path transferred to controller");
        return request.getContextPath() + Commands.VIEW_CART_COMMAND;
    }
}
