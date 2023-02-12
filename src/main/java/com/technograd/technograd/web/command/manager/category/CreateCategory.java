package com.technograd.technograd.web.command.manager.category;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.entity.Category;
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


public class CreateCategory extends Command {

    private static final long serialVersionUID = -5068493456362968676L;
    private final CategoryDAO categoryDAO;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    public CreateCategory() {
        categoryDAO = new CategoryDAO();
    }
    public CreateCategory(CategoryDAO dao) {
        categoryDAO = dao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("CreateCategory execute started");
        HttpSession session = request.getSession();

        String nameUa = request.getParameter("name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = request.getParameter("name_en");
        logger.trace("name_en ->" + nameEn);

        Category category = new Category(nameUa, nameEn);
        try {
            categoryDAO.createCategory(category);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.category.create";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCategories";
        }
        logger.info("CreateCategory execute finished, path transferred to controller");
        return request.getContextPath() + Commands.VIEW_CATEGORIES;
    }
}
