package com.technograd.technograd.web.command.manager.product;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.validator.ProductValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

public class UpdateProduct extends Command {

    private static final long serialVersionUID = 4565089866717159322L;
    private static final Logger logger = LogManager.getLogger(UpdateProduct.class.getName());
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CompanyDAO companyDAO;

    public UpdateProduct(ProductDAO productDAO, CategoryDAO categoryDAO, CompanyDAO companyDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.companyDAO = companyDAO;
    }

    public UpdateProduct() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
        this.companyDAO = new CompanyDAO();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, AppException {
        HttpSession session = req.getSession();
        logger.info("UpdateProduct execute started");
        Product product;
        String id = req.getParameter("product_update_id");
        try{
            product = readProductDataFromRequest(req);
        } catch (Exception e){
            String errorMessage = "product.update.wrong.data";
            session.setAttribute("errorMessage", errorMessage);
            return req.getContextPath() + Commands.VIEW_MENU_COMMAND;
        }

        ProductValidator productValidator = new ProductValidator();
        if(product == null ||!productValidator.validator(product)){
            String errorMessage = "product.update.wrong.data";
            session.setAttribute("errorMessage", errorMessage);
            return req.getContextPath() + Commands.VIEW_MENU_COMMAND;
        }


        try{
            productDAO.updateProduct(product);
        } catch (Exception e){
            String errorMessage = "product.update.wrong.something";
            session.setAttribute("errorMessage", errorMessage);
            return req.getContextPath() + Commands.VIEW_MENU_COMMAND;
        }
        logger.info("UpdateProduct execute finished");
        return req.getContextPath() + "/controller?command=viewProductPage&id=" + id;
    }
    private static Product readProductDataFromRequest(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("product_update_id"));
        logger.trace("product_update_id ->" + id);
        String nameUa = req.getParameter("new_name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = req.getParameter("new_name_en");
        logger.trace("name_en ->" + nameEn);

        BigDecimal price = new BigDecimal(req.getParameter("new_price"));
        logger.trace("price ->" + price);
        int weight = Integer.parseInt(req.getParameter("new_weight"));
        logger.trace("weight ->" + weight);

        int count = Integer.parseInt(req.getParameter("new_count"));
        logger.trace("count ->" + count);
        int warranty = Integer.parseInt(req.getParameter("new_warranty"));
        logger.trace("warranty ->" + warranty);

        String titleUa = req.getParameter("new_title_ua");
        logger.trace("title_ua ->" + titleUa);
        String titleEn = req.getParameter("new_title_en");
        logger.trace("title_en ->" + titleEn);

        String descriptionUa = req.getParameter("new_description_ua");
        logger.trace("description_ua ->" + descriptionUa);
        String descriptionEn = req.getParameter("new_description_en");
        logger.trace("description_en ->" + descriptionEn);
        int categoryId = Integer.parseInt(req.getParameter("new_category_id"));
        logger.trace("category_id ->" + categoryId);
        int companyId = Integer.parseInt(req.getParameter("new_company_id"));
        logger.trace("company_id ->" + companyId);

        Category category = new Category();
        category.setId(categoryId);
        Company company = new Company();
        company.setId(companyId);

        Product product = new Product();
        product.setNameUa(nameUa);
        product.setNameEn(nameEn);

        product.setId(id);
        product.setPrice(price);
        product.setWeight(weight);
        product.setCount(count);
        product.setWarranty(warranty);

        product.setCompany(company);
        product.setCategory(category);

        product.setTitleUa(titleUa);
        product.setTitleEn(titleEn);
        product.setDescriptionEn(descriptionEn);
        product.setDescriptionUa(descriptionUa);
        return product;
    }

}
