package com.technograd.technograd.web.command.manager.category;

import com.technograd.technograd.dao.CategoryDAO;
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

public class DeleteCategory extends Command {

    private static final long serialVersionUID = 2585592730326793675L;
    private final CategoryDAO categoryDAO;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    public DeleteCategory(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public DeleteCategory() {
        this.categoryDAO = new CategoryDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("DeleteCategory execute started");
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("delete_by_id"));
        logger.trace("delete_by_id ->" + id);
        try{
            categoryDAO.deleteById(id);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.category.delete";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_CATEGORIES;
        }

        logger.info("DeleteCategory execute finished, path transferred to controller");
        return request.getContextPath() + Commands.VIEW_CATEGORIES;
    }
}
