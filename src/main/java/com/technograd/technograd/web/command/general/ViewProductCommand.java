package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.Company;
import com.technograd.technograd.dao.entity.Product;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewProductCommand extends Command {

    private static final long serialVersionUID = -1188518930042171361L;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CompanyDAO companyDAO;

    public ViewProductCommand(ProductDAO productDAO, CategoryDAO categoryDAO, CompanyDAO companyDAO) {
        this.productDAO = productDAO;
        this.companyDAO = companyDAO;
        this.categoryDAO = categoryDAO;
    }

    public ViewProductCommand() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        companyDAO = new CompanyDAO();
    }

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        int id;
       List<Category> categoryList = new ArrayList<>();
       List<Company> companyList = new ArrayList<>();
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException exception){
            throw new AppException(exception);
        }
        Product product;
        try{
            product = productDAO.getProductById(id);
            categoryList = categoryDAO.getAllCategories();
            companyList = companyDAO.getAllCompanies();
        } catch (DBException e) {
            throw new AppException(e);
        }

        if(product == null){
            throw new AppException("message");
        }

        request.setAttribute("categoryList", categoryList);
        request.setAttribute("companyList", companyList);
        request.setAttribute("product", product);
        return Path.VIEW_PRODUCT_PAGE;
    }
}
