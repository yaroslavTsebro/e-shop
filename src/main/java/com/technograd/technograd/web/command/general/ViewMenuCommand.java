package com.technograd.technograd.web.command.general;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CharacteristicDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.dao.entity.Product;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.customer.CreateCategory;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    private final CharacteristicDAO characteristicDAO;

    public ViewMenuCommand(ProductDAO productDAO, CategoryDAO categoryDAO, CharacteristicDAO characteristicDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.characteristicDAO = characteristicDAO;
    }
    public ViewMenuCommand() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        characteristicDAO = new CharacteristicDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("View menu command started");

        HttpSession session = request.getSession();

        List<Product> productList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        List<Characteristic> characteristicList = new ArrayList<>();

        try{
            productList = Stream.concat(productList.stream(), ProductDAO.getAllProducts().stream()).collect(Collectors.toList());
        } catch (DBException exception) {
            String errorMessage = "product.dao.find.all";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }
        logger.debug("Set session attribute : updated current productList info => " + productList);
        session.setAttribute("productList", productList);

        try{
            categoryList = Stream.concat(categoryList.stream(), CategoryDAO.getAllCategories().stream()).collect(Collectors.toList());
        } catch (DBException exception) {
            String errorMessage = "category.dao.find.all";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }
        logger.debug("Set session attribute : updated current categoryList info => " + productList);
        session.setAttribute("categoryList", categoryList);

        try{
            characteristicList = Stream.concat(characteristicList.stream(), CharacteristicDAO.getAllCharacteristics().stream()).collect(Collectors.toList());
        } catch (DBException exception) {
            String errorMessage = "characteristic.dao.find.all";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }
        logger.debug("Set session attribute : updated current characteristicList info => " + characteristicList);
        session.setAttribute("characteristicList", characteristicList);

        logger.debug("View menu command is finished");
        return Path.MENU_PAGE;
    }
}
