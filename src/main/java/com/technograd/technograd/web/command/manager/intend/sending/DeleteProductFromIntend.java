package com.technograd.technograd.web.command.manager.intend.sending;

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

public class DeleteProductFromIntend extends Command {

    private static final Logger logger = LogManager.getLogger(DeleteProductFromIntend.class.getName());
    private static final long serialVersionUID = -58183775732463454L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("DeleteProductFromIntend execute started");

        int userId = Integer.parseInt(request.getParameter("user_id"));
        logger.trace("user_id ->" + userId);

        int id = Integer.parseInt(request.getParameter("delete_li_by_id"));
        logger.trace("delete_li_by_id ->" + id);

        try {
            ListIntendDAO.deleteListIntendById(id, userId);
            logger.trace("listIntend with this id was deleted:" + id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        logger.info("DeleteProductFromIntend execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCart";
    }
}