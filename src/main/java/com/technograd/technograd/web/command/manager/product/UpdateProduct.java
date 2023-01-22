package com.technograd.technograd.web.command.manager.product;

import com.technograd.technograd.dao.PhotoDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateProduct extends Command {

    private static final long serialVersionUID = 4565089866717159322L;
    private static final Logger logger = LogManager.getLogger(UpdateProduct.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, AppException {
        try{
            logger.info("AddProductServlet execute started");
            Product product = readProductDataFromRequest(req);

            ProductDAO.updateProduct(product);
            resp.getWriter().print("The files uploaded successfully.");
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return req.getContextPath() + "/product";
    }

    private static Product readProductDataFromRequest(HttpServletRequest req){
        String id = req.getParameter("product_update_id");
        logger.trace("product_update_id ->" + id);
        String nameUa = req.getParameter("name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = req.getParameter("name_en");
        logger.trace("name_en ->" + nameEn);

        BigDecimal price = new BigDecimal(req.getParameter("price"));
        logger.trace("price ->" + price);
        int weight = Integer.parseInt(req.getParameter("weight"));
        logger.trace("weight ->" + weight);

        int count = Integer.parseInt(req.getParameter("price"));
        logger.trace("count ->" + count);
        int warranty = Integer.parseInt(req.getParameter("warranty"));
        logger.trace("warranty ->" + warranty);

        String titleUa = req.getParameter("title_ua");
        logger.trace("title_ua ->" + titleUa);
        String titleEn = req.getParameter("title_en");
        logger.trace("title_en ->" + titleEn);

        String descriptionUa = req.getParameter("description_ua");
        logger.trace("description_ua ->" + descriptionUa);
        String descriptionEn = req.getParameter("description_en");
        logger.trace("description_en ->" + descriptionEn);
        int categoryId = Integer.parseInt(req.getParameter("category_id"));
        logger.trace("category_id ->" + categoryId);
        int companyId = Integer.parseInt(req.getParameter("company_id"));
        logger.trace("company_id ->" + companyId);

        Category category = new Category();
        category.setId(categoryId);
        Company company = new Company();
        company.setId(companyId);

        Product product = new Product();
        product.setNameUa(nameUa);
        product.setNameEn(nameEn);

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