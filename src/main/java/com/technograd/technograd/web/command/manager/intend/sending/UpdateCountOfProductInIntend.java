package com.technograd.technograd.web.command.manager.intend.sending;

import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.ProductDAO;
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


public class UpdateCountOfProductInIntend extends Command {

    private static final long serialVersionUID = 537159207045482212L;

    private static final Logger logger = LogManager.getLogger(UpdateCountOfProductInIntend.class.getName());

    private final ListIntendDAO listIntendDAO;
    private final ProductDAO productDAO;

    public UpdateCountOfProductInIntend() {
        this.listIntendDAO = new ListIntendDAO();
        this.productDAO = new ProductDAO();
    }

    public UpdateCountOfProductInIntend(ListIntendDAO listIntendDAO, ProductDAO productDAO) {
        this.listIntendDAO = listIntendDAO;
        this.productDAO = productDAO;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ChangeCountOfProductInIntendAsAdmin execute started");
        HttpSession session = request.getSession();

        int userId = Integer.parseInt(request.getParameter("user_id"));
        logger.trace("user_id ->" + userId);


        int intendId = Integer.parseInt(request.getParameter("intend_id"));
        logger.trace("intend_id ->" + intendId);

        int id = Integer.parseInt(request.getParameter("update_by_id"));
        logger.trace("update_by_id ->" + id);
        int newCount = Integer.parseInt(request.getParameter("updated_li_count"));
        logger.trace("updated_li_count ->" + newCount);

        int productId = Integer.parseInt(request.getParameter("update_by_product_id"));
        logger.trace("update_by_product_id ->" + productId);
        int count = Integer.parseInt(request.getParameter("update_by_product_count"));
        logger.trace("update_by_product_count ->" + count);

        if(count == newCount){
            session.setAttribute("errorMessage", "sending.admin.change.count");
            return request.getContextPath() + "/controller?command=viewCurrentSending&id=" + intendId;
        }

        int productCount;
        try {
            productCount = productDAO.getProductById(productId).getCount();
        } catch (DBException e) {
            session.setAttribute("errorMessage", "sending.admin.change.count");
            return request.getContextPath() + "/controller?command=viewCurrentSending&id=" + intendId;
        }

        if(count > productCount){
            session.setAttribute("errorMessage", "sending.admin.change.count");
            return request.getContextPath() + "/controller?command=viewCurrentSending&id=" + intendId;
        }

        if(newCount <= 0){
            try {
                listIntendDAO.deleteListIntendById(id, userId);
                logger.trace("listIntend with this id was deleted:" + id);
            } catch (DBException e) {
                session.setAttribute("errorMessage", "sending.admin.change.count");
                return request.getContextPath() + "/controller?command=viewCurrentSending&id=" + intendId;
            }
        } else{
            try {
                listIntendDAO.updateCountInListIntendById(id, newCount);
                logger.trace("listIntend with this id was updated:" + id);
            } catch (DBException e) {
                session.setAttribute("errorMessage", "sending.admin.change.count");
                return request.getContextPath() + "/controller?command=viewCurrentSending&id=" + intendId;
            }
        }


        logger.info("ChangeCountOfProductInIntendAsAdmin execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCurrentSending&id=" + intendId;
    }

}
