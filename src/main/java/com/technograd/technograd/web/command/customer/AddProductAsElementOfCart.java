package com.technograd.technograd.web.command.customer;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.UserDAO;
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
import java.math.BigDecimal;

public class AddProductAsElementOfCart extends Command {

    private static final long serialVersionUID = 238851840190904254L;
    private static final Logger logger = LogManager.getLogger(AddProductAsElementOfCart.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("AddProductAsElementOfCart execute started");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        int productCount = Integer.parseInt(request.getParameter("product_count"));
        logger.trace("product_count ->" + productCount);

        int addToCartCount = Integer.parseInt(request.getParameter("add_to_cart_count"));
        logger.trace("add_to_cart_count ->" + addToCartCount);

        int productId = Integer.parseInt(request.getParameter("product_id"));
        logger.trace("product_id ->" + productId);

        String price = request.getParameter("product_price");
        System.out.println(price);
        BigDecimal productPrice = new BigDecimal(price);
        logger.trace("product_price ->" + productPrice);


        if(productCount < addToCartCount){
            throw new RuntimeException();
        }


        Intend cart;
        try{
            cart = IntendDAO.findCartById(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if(cart == null){
            int employeeId;
            try{
                 employeeId = UserDAO.getManagerWithLowestCountOfIntends();
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            try{
                IntendDAO.createIntendSending(id, employeeId);
                cart = IntendDAO.findCartById(id);
                logger.trace("Intend with this id was inserted:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            try {
                ListIntendDAO.createListIntend(cart.getId(), addToCartCount, productId, productPrice);
                logger.trace("listIntend with this id was inserted:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        } else{
            try {
                ListIntendDAO.createListIntend(cart.getId(), addToCartCount, productId, productPrice);
                logger.trace("listIntend with this id was deleted:" + id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }


        logger.info("AddProductAsElementOfCart execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCart";
    }
}
