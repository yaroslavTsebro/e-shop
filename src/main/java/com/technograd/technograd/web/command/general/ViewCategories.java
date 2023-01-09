package com.technograd.technograd.web.command.general;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.web.error.AppException;
import com.technograd.technograd.web.error.DBException;
import com.technograd.technograd.web.localization.LocalizationUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;


@WebServlet(name = "ViewCategories", value = "/category")
public class ViewCategories extends HttpServlet {


    private static final long serialVersionUID = 4282517201420173831L;
    private final CategoryDAO categoryDAO;

    public ViewCategories(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
    public ViewCategories() {
        this.categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ResourceBundle userRb = LocalizationUtils.getCurrentRb(session);
        ResourceBundle enRb = LocalizationUtils.getEnglishRb();

        List<Category> categoryList = null;
        try {
            categoryList = CategoryDAO.getAllCategories();
            System.out.println(categoryList.toString());
        } catch (DBException exception) {
            try {
                throw new AppException(exception);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        } finally {
            req.setAttribute("categoryList", categoryList);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/general/category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameUa = req.getParameter("name_ua");
        String nameEn = req.getParameter("name_en");
        Category category = new Category(nameUa, nameEn);
        System.out.println(category);
        try {
            categoryDAO.createCategory(category);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        //doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}