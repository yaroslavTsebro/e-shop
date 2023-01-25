package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Product;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ViewProductCommand extends Command {

    private static final long serialVersionUID = -1188518930042171361L;
    private final ProductDAO productDAO;

    public ViewProductCommand(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ViewProductCommand() {
        productDAO = new ProductDAO();
    }

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException exception){
            throw new AppException(exception);
        }
        Product product;
        try{
            product = productDAO.getProductById(id);
        } catch (DBException e) {
            throw new AppException(e);
        }

        if(product == null){
            throw new AppException("message");
        }

        request.setAttribute("product", product);
        return Path.VIEW_PRODUCT_PAGE;
    }
}
