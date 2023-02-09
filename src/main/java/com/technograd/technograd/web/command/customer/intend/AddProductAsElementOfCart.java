package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.ListIntend;
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
import java.math.BigDecimal;

public class AddProductAsElementOfCart extends Command {

    private static final long serialVersionUID = 238851840190904254L;
    private static final Logger logger = LogManager.getLogger(AddProductAsElementOfCart.class.getName());
    private final IntendDAO intendDAO;
    private final ListIntendDAO listIntendDAO;

    public AddProductAsElementOfCart() {
        this.intendDAO = new IntendDAO();
        this.listIntendDAO = new ListIntendDAO();
    }

    public AddProductAsElementOfCart(IntendDAO intendDAO, ListIntendDAO listIntendDAO) {
        this.intendDAO = intendDAO;
        this.listIntendDAO = listIntendDAO;
    }

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

        if(addToCartCount <= 0){
            String errorMessage = "cart.product.count.isnt.valid";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_MENU_COMMAND;
        }


        if(productCount < addToCartCount){
            String errorMessage = "cart.product.count.less.than.need";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_MENU_COMMAND;
        }

        Intend cart;
        try{
            cart = intendDAO.findCartById(id);
        } catch (DBException e) {
            String errorMessage = "cart.product.error";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_MENU_COMMAND;
        }

        if(cart == null){
            try{
                intendDAO.createIntendSending(id);
                cart = intendDAO.findCartById(id);
                logger.trace("Intend with this id was inserted:" + id);
            } catch (DBException e) {
                String errorMessage = "cart.product.error";
                session.setAttribute("errorMessage", errorMessage);
                return request.getContextPath() + Commands.VIEW_MENU_COMMAND;
            }
            try {
                listIntendDAO.createListIntend(cart.getId(), addToCartCount, productId, productPrice);
                logger.trace("listIntend with this id was inserted:" + id);
            } catch (DBException e) {
                String errorMessage = "cart.product.error";
                session.setAttribute("errorMessage", errorMessage);
                return request.getContextPath() + Commands.VIEW_MENU_COMMAND;
            }
        } else{
            try {
                ListIntend checkCartForProduct = listIntendDAO.checkCartForProduct(productId, cart.getId(), id);
                    if(checkCartForProduct == null){
                        listIntendDAO.createListIntend(cart.getId(), addToCartCount, productId, productPrice);
                    } else {
                        listIntendDAO.updateCountInListIntendByIdInCart(checkCartForProduct.getId(), addToCartCount, id);
                    }
                logger.trace("listIntend with this id was inserted:" + id);
            } catch (DBException e) {
                String errorMessage = "cart.product.error";
                session.setAttribute("errorMessage", errorMessage);
                return request.getContextPath() + Commands.VIEW_MENU_COMMAND;
            }
        }


        logger.info("AddProductAsElementOfCart execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCart";
    }
}
