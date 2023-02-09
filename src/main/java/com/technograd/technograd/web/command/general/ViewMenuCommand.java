package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.Company;
import com.technograd.technograd.dao.entity.Product;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.manager.category.CreateCategory;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ViewMenuCommand extends Command {

    private static final long serialVersionUID = -1227114065336794942L;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CompanyDAO companyDAO;

    public ViewMenuCommand(ProductDAO productDAO, CategoryDAO categoryDAO, CompanyDAO companyDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.companyDAO = companyDAO;
    }
    public ViewMenuCommand() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
        this.companyDAO = new CompanyDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("ViewMenuCommand command started");

        List<Product> productList = null;
        List<Category> categoryList = new ArrayList<>();
        List<Company> companyList = new ArrayList<>();
        int recordsPerPage = 2;
        int currentPage;
        try {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } catch (NumberFormatException exception) {
            currentPage = 0;
        }

        String searchQuery = request.getParameter("searchQuery");
        String sort = request.getParameter("sortBy");
        String category =request.getParameter("category");
        String company = request.getParameter("company");
        int categoryId = 0;
        int companyId = 0;
        if(category != null){
            categoryId = Integer.parseInt(category);
            currentPage = 0;
        }

        if(company != null){
            companyId = Integer.parseInt(company);
            currentPage = 0;
        }


        int numberOfRows= -1;
        try{
            if(searchQuery == null || searchQuery.isEmpty()){
                String query = productDAO.menuQueryBuilder(searchQuery,true,companyId, categoryId, sort);
                String countQuery = productDAO.menuQueryBuilder(searchQuery,false,companyId, categoryId, sort);
                productList =  productDAO.getAllReducedProducts(query, recordsPerPage, currentPage * recordsPerPage);
                numberOfRows = productDAO.countOfRows(countQuery);
            } else{
                String query = productDAO.menuQueryBuilder(searchQuery,true,companyId, categoryId, sort);
                productList =  productDAO.getAllReducedProducts(query);
            }
        } catch (DBException exception) {
            String errorMessage = "product.dao.find.all";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }
        request.setAttribute("productList", productList);


        try{
            categoryList = Stream.concat(categoryList.stream(), categoryDAO.getAllCategories().stream()).collect(Collectors.toList());
        } catch (DBException exception) {
            String errorMessage = "category.dao.find.all";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }
        logger.debug("Set session attribute : updated current categoryList info => " + categoryList);
        request.setAttribute("categoryList", categoryList);

        try{
            companyList = Stream.concat(companyList.stream(), companyDAO.getAllCompanies().stream()).collect(Collectors.toList());
        } catch (DBException exception) {
            String errorMessage = "characteristic.dao.find.all";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }
        logger.debug("Set session attribute : updated current companyList info => " + companyList);
        request.setAttribute("companyList", companyList);

        if (numberOfRows != -1){
            int nOfPages = numberOfRows / recordsPerPage;

            if (numberOfRows % recordsPerPage > 0) {
                nOfPages++;
            }

            request.setAttribute("nOfPages", nOfPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("recordsPerPage", recordsPerPage);
        }

        logger.debug("View menu command is finished");
        return Path.MENU_PAGE;
    }
}
