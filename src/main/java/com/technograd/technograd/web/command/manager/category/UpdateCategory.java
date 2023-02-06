package com.technograd.technograd.web.command.manager.category;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.entity.Category;
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

public class UpdateCategory extends Command {

    private static final long serialVersionUID = 1108101221128289750L;
    private static final Logger logger = LogManager.getLogger(UpdateCategory.class.getName());
    private final CategoryDAO categoryDAO;

    public UpdateCategory(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public UpdateCategory() {
        this.categoryDAO = new CategoryDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("UpdateCategory execute started");
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("update_by_id"));
        logger.trace("update_by_id ->" + id);
        String nameUa = request.getParameter("updated_name_ua");
        logger.trace("updated_name_ua ->" + nameUa);
        String nameEn = request.getParameter("updated_name_en");
        logger.trace("updated_name_en ->" + nameEn);

        Category category = new Category(id ,nameUa, nameEn);
        try {
            categoryDAO.updateCategory(category);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.category.update";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCategories";
        }
        logger.info("UpdateCategory execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCategories";
    }
}
