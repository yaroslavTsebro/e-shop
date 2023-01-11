package com.technograd.technograd.web.command.customer;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;


public class CreateCategory extends Command {

    private static final long serialVersionUID = -5068493456362968676L;
    private final CategoryDAO categoryDAO;
    Logger logger = Logger.getLogger(CreateCategory.class.getName());

    public CreateCategory() {
        categoryDAO = new CategoryDAO();
    }
    public CreateCategory(CategoryDAO dao) {
        categoryDAO = dao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("CreateCategory execute started");
        String nameUa = request.getParameter("name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = request.getParameter("name_en");
        logger.trace("name_en ->" + nameEn);

        Category category = new Category(nameUa, nameEn);
        try {
            categoryDAO.createCategory(category);
        } catch (DBException e) {
            logger.error("errorMessage --> " + e);
            throw new AppException(e);
        }
        logger.info("CreateCategory execute finished, path transferred to controller");
        return request.getContextPath() + "/category";
    }
}
