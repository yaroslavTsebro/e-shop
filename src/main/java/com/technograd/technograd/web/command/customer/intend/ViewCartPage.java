package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.ListIntend;
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
    private final IntendDAO intendDAO;
    private final ListIntendDAO listIntendDAO;

    public ViewCartPage() {
        this.intendDAO = new IntendDAO();
        this.listIntendDAO = new ListIntendDAO();
    }

    public ViewCartPage(IntendDAO intendDAO, ListIntendDAO listIntendDAO) {
        this.intendDAO = intendDAO;
        this.listIntendDAO = listIntendDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewCartPage execute started");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        Intend intend;
        try{
            intend = intendDAO.findCartById(id);
            logger.trace("cart ->" + intend);
            if(intend == null){
                return Path.CART_PAGE;
            }
            for (ListIntend li: intend.getListIntends()) {
                if(0 != (li.getProductPrice()).compareTo(li.getProduct().getPrice())){
                    try {
                        listIntendDAO.updatePriceInListIntendById(li.getProduct().getPrice(), li.getId());
                        li.setProductPrice(li.getProduct().getPrice());
                    } catch (DBException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (DBException e) {
            throw new RuntimeException(e);
        }


        System.out.println(intend);
        request.setAttribute("cart", intend);
        logger.info("ViewCartPage execute finished, path transferred to controller");
        return Path.CART_PAGE;
    }
}
