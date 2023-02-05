package com.technograd.technograd.web.command.manager.category;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class ViewCategory extends Command {

    private static final long serialVersionUID = 8389809346058200398L;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    private final CategoryDAO categoryDAO;

    public ViewCategory() {
        categoryDAO = new CategoryDAO();
    }
    public ViewCategory(CategoryDAO dao) {
        categoryDAO = dao;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewCategory execute started");

        List<Category> categoryList = null;
        try {
            categoryList = categoryDAO.getAllCategories();
            logger.trace("categoryList ->" + categoryList);
        } catch (DBException exception) {
            try {
                throw new AppException(exception.getMessage());
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        } finally {
            request.setAttribute("categoryList", categoryList);
        }
        logger.info("ViewCategory execute finished, path transferred to controller");
        return Path.CATEGORY_PAGE;
    }
}
