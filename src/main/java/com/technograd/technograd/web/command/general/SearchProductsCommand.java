package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Product;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.manager.category.CreateCategory;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class SearchProductsCommand extends Command {

    private static final long serialVersionUID = 3798944906527707888L;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());
    private final ProductDAO productDAO;

    public SearchProductsCommand() {
        productDAO = new ProductDAO();
    }

    public SearchProductsCommand(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("View search page command is stared");

        int recordsPerPage = 4;

        int currentPage;
        if (request.getParameter("currentPage") == null) {
            currentPage = 1;
        } else {
            try {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            } catch (NumberFormatException exception) {
                String errorMessage = "error.occurred";
                logger.error("errorMessage --> " + exception);
                throw new AppException(errorMessage);
            }
        }
        List<Product> allProducts;
        int numberOfRows;
        String sort = request.getParameter("sort");

        try {
            numberOfRows = productDAO.getAllProducts().size();
        } catch (DBException exception) {
            exception.printStackTrace();
            throw new AppException();
        }
        if ("byName".equals(sort)) {
            try {
                allProducts = productDAO.getAllProducts();
            } catch (DBException exception) {
                exception.printStackTrace();
                throw new AppException();
            }
        } else if ("byPrice".equals(sort)) {
            try {
                allProducts = productDAO.getAllProducts();
            } catch (DBException exception) {
                exception.printStackTrace();
                throw new AppException();
            }
        } else {
            try {
                allProducts = productDAO.getAllProducts();
            } catch (DBException exception) {
                exception.printStackTrace();
                throw new AppException();
            }
        }
        request.setAttribute("sort", request.getParameter("sort"));

        request.setAttribute("allProducts", allProducts);

        logger.debug("Number of rows of products" + numberOfRows);
        int nOfPages = numberOfRows / recordsPerPage;
        logger.debug("nOfPages ===>>> " + nOfPages);

        if (numberOfRows % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("nOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        logger.debug("View search page command is finished");
        return Path.MENU_PAGE;
    }
}
