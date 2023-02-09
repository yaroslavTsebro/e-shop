package com.technograd.technograd.web.command.manager.product;

import com.technograd.technograd.dao.ProductCharacteristicDAO;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DeleteCharacteristic extends Command {


    private static final long serialVersionUID = 738723467696073002L;
    private static final Logger logger = LogManager.getLogger(AddProductServlet.class.getName());
    private final ProductCharacteristicDAO productCharacteristicDAO;

    public DeleteCharacteristic() {
        this.productCharacteristicDAO = new ProductCharacteristicDAO();
    }

    public DeleteCharacteristic(ProductCharacteristicDAO productCharacteristicDAO) {
        this.productCharacteristicDAO = productCharacteristicDAO;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, AppException {
        int id = Integer.parseInt(req.getParameter("product_characteristic_id"));
        logger.trace("product_characteristic_id ->" + id);
        try{
            productCharacteristicDAO.deleteById(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return req.getContextPath() + "product";
    }

}
