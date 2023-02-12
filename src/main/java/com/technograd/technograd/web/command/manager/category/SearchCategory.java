package com.technograd.technograd.web.command.manager.category;

import com.technograd.technograd.Path;
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
import java.util.List;

public class SearchCategory extends Command {

    private static final long serialVersionUID = 4918863306195670390L;
    private static final Logger logger = LogManager.getLogger(SearchCategory.class.getName());

    private final CategoryDAO categoryDAO;

    public SearchCategory() {
        categoryDAO = new CategoryDAO();
    }
    public SearchCategory(CategoryDAO dao) {
        categoryDAO = dao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("SearchCategory execute started");
        HttpSession session = request.getSession();

        String pattern = request.getParameter("pattern");
        if (pattern == null || pattern.isEmpty()) {
            return request.getContextPath() + Commands.VIEW_CATEGORIES;
        }
        logger.debug("Pattern is => " + pattern);
        List<Category> categoryList = null;
        try {
            categoryList = categoryDAO.searchCategories(pattern);
            logger.trace("categoryList ->" + categoryList);
        } catch (DBException exception) {
            logger.trace("error ->" + exception);
            String errorMessage = "error.category.search";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCategories";
        } finally {
            request.setAttribute("categoryList", categoryList);
        }
        logger.info("SearchCategory execute finished, path transferred to controller");
        return Path.CATEGORY_PAGE;
    }
}
