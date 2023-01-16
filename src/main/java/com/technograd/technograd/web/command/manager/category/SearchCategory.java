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

public class SearchCategory extends Command {

    private static final long serialVersionUID = 4918863306195670390L;
    private static final Logger logger = LogManager.getLogger(SearchCategory.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("SearchCategory execute started");

        String pattern = request.getParameter("pattern");
        if (pattern == null || pattern.isEmpty()) {
            String errorMessage = "error.occurred";
            logger.error("Search pattern is empty");
            throw new AppException(errorMessage);
        }
        logger.debug("Pattern is => " + pattern);
        List<Category> categoryList = null;
        try {
            categoryList = CategoryDAO.searchCategories(pattern);
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
        logger.info("SearchCategory execute finished, path transferred to controller");
        return Path.CATEGORY_PAGE;
    }
}
