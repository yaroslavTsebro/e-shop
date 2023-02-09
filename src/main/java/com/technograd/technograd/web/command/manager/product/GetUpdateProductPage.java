package com.technograd.technograd.web.command.manager.product;

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
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUpdateProductPage extends Command {

    private static final long serialVersionUID = 5182307494095740577L;
    private static final Logger logger = LogManager.getLogger(UpdateProduct.class.getName());
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CompanyDAO companyDAO;

    public GetUpdateProductPage(ProductDAO productDAO, CategoryDAO categoryDAO, CompanyDAO companyDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.companyDAO = companyDAO;
    }

    public GetUpdateProductPage() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
        this.companyDAO = new CompanyDAO();
    }


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, AppException {
        int id = Integer.parseInt(req.getParameter("update_id"));
        HttpSession session = req.getSession();

        List<Category> categoryList = new ArrayList<>();
        List<Company> companyList = new ArrayList<>();
        Product product = null;
        try{
            product = productDAO.getProductById(id);
            categoryList = categoryDAO.getAllCategories();
            companyList = companyDAO.getAllCompanies();
        } catch (DBException e){
            session.setAttribute("errorMessage", "error.manager.update.product");
        }
        req.setAttribute("categoryList", categoryList);
        req.setAttribute("product", product);
        req.setAttribute("companyList", companyList);
        req.setAttribute("product_update_id", id);
        return Path.UPDATE_PRODUCT_PAGE;
    }
}
