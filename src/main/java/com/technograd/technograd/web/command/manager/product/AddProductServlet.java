package com.technograd.technograd.web.command.manager.product;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CharacteristicDAO;
import com.technograd.technograd.dao.CompanyDAO;
import com.technograd.technograd.dao.PhotoDAO;
import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MultipartConfig(   fileSizeThreshold = 1024 * 1024,
                    maxFileSize = 1024 * 1024 * 5,
                    maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "product", value = "/product")
public class AddProductServlet extends HttpServlet {

    private static final long serialVersionUID = -2734001995019003200L;
    private static final Logger logger = LogManager.getLogger(AddProductServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AddProductServlet execute started");

        List<Category> categoryList = null;
        List<Company> companyList = null;
        List<Characteristic> characteristicList = null;
        try {
            categoryList = CategoryDAO.getAllCategories();
            logger.trace("categoryList ->" + categoryList);
            companyList = CompanyDAO.getAllCompanies();
            logger.trace("companyList ->" + categoryList);
            characteristicList = CharacteristicDAO.getAllCharacteristics();
            logger.trace("characteristicList ->" + characteristicList);
        } catch (DBException exception) {
            try {
                throw new AppException(exception.getMessage());
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        } finally {
            request.setAttribute("categoryList", categoryList);
            request.setAttribute("companyList", companyList);
            request.setAttribute("characteristicList", characteristicList);
        }
        logger.info("AddProductServlet execute finished, path transferred to controller");
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.PRODUCT_PAGE);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            logger.info("AddProductServlet execute started");
            int id = Integer.parseInt(req.getParameter("product_create_id"));
            logger.trace("product_create_id ->" + id);
            String[] characteristicIds = req.getParameterValues("characteristic_id");
            String[] characteristicValues = req.getParameterValues("characteristic_value");
            Product product = new Product();

            List<Photo> photos = new ArrayList<>();
            int nameId = PhotoDAO.getNextId();
            if(nameId == -1){
                throw new AppException();
            }

            int tmpNameId = nameId;
            for (int i = 0; i < req.getParts().size(); i++) {
                photos.add(new Photo(Integer. toString(tmpNameId)));
                tmpNameId++;
            }
            product.setPhoto(photos);




            int tmpNameIdForFiles = nameId;
            for (Part part : req.getParts()) {
                part.write("C:\\Users\\User\\Desktop\\" + tmpNameIdForFiles + ".jpg");
                tmpNameIdForFiles++;
            }
            resp.getWriter().print("The files uploaded successfully.");
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        super.doPost(req, resp);
    }
}
