package com.technograd.technograd.web.command.manager.product;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.*;
import com.technograd.technograd.dao.entity.*;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig(   fileSizeThreshold = 1024 * 1024,
                    maxFileSize = 1024 * 1024 * 5,
                    maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "product", value = "/product")
public class AddProductServlet extends HttpServlet {

    private static final long serialVersionUID = -2734001995019003200L;
    private static final Logger logger = LogManager.getLogger(AddProductServlet.class.getName());

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CompanyDAO companyDAO;
    private final CharacteristicDAO characteristicDAO;
    private final PhotoDAO photoDAO;

    public AddProductServlet(ProductDAO p, CategoryDAO cat, CompanyDAO com, CharacteristicDAO cha, PhotoDAO photoDAO) {
        this.productDAO = p;
        this.categoryDAO = cat;
        this.companyDAO = com;
        this.characteristicDAO = cha;
        this.photoDAO = photoDAO;
    }

    public AddProductServlet() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
        this.companyDAO = new CompanyDAO();
        this.characteristicDAO = new CharacteristicDAO();
        this.photoDAO = new PhotoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if(!user.getPost().equals(Post.MANAGER)){
            String errorMessage = "product.create.wrong.post";
            session.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Path.LOGIN_PAGE);
            dispatcher.forward(request, response);
        }
        logger.info("AddProductServlet execute started");

        List<Category> categoryList = null;
        List<Company> companyList = null;
        List<Characteristic> characteristicList = null;
        try {
            categoryList = categoryDAO.getAllCategories();
            logger.trace("categoryList ->" + categoryList);
            companyList = companyDAO.getAllCompanies();
            logger.trace("companyList ->" + categoryList);
            characteristicList =  characteristicDAO.getAllCharacteristics();
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
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(!user.getPost().equals(Post.MANAGER)){
            String errorMessage = "product.create.wrong.post";
            session.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = req.getRequestDispatcher(Path.LOGIN_PAGE);
            dispatcher.forward(req, resp);
        }
        try{
            logger.info("AddProductServlet execute started");
            Product product = readProductDataFromRequest(req);

            String[] characteristicIds = req.getParameterValues("characteristic_id");
            String[] characteristicValues = req.getParameterValues("characteristic_value");
            List<Characteristic> characteristics = new ArrayList<>();
            List<ProductCharacteristic> productCharacteristics = new ArrayList<>();
            if(characteristicIds.length == characteristicValues.length){
                for (int i = 0; i < characteristicIds.length; i++) {
                    Characteristic tmpChar = new Characteristic();
                    tmpChar.setId(Integer.parseInt(characteristicIds[i]));
                    characteristics.add(tmpChar);

                    ProductCharacteristic tmpProdChar = new ProductCharacteristic();
                    tmpProdChar.setValue(characteristicValues[i]);
                    productCharacteristics.add(tmpProdChar);
                }
            } else{
                throw new AppException();
            }
            product.setProductCharacteristics(productCharacteristics);


            List<Part> fileParts = req.getParts().stream().filter(part -> "file".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());

            List<Photo> photos = new ArrayList<>();
            int nameId = photoDAO.getNextId();
            if(nameId == -1){
                throw new AppException();
            }

            int tmpNameId = nameId;
            for (Part filePart : fileParts) {
                photos.add(new Photo(Integer. toString(tmpNameId) + ".jpg"));
                tmpNameId++;
            }
            product.setPhotos(photos);

            productDAO.createProductAndPhotosAndCharacteristics(product, characteristics);
            writePhotos(fileParts, nameId);
            resp.getWriter().print("The files uploaded successfully.");
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        resp.sendRedirect(req.getContextPath() + "/product");
    }


    private static Product readProductDataFromRequest(HttpServletRequest req){
        String nameUa = req.getParameter("name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = req.getParameter("name_en");
        logger.trace("name_en ->" + nameEn);

        BigDecimal price = new BigDecimal(req.getParameter("price"));
        logger.trace("price ->" + price);
        int weight = Integer.parseInt(req.getParameter("weight"));
        logger.trace("weight ->" + weight);

        int count = Integer.parseInt(req.getParameter("count"));
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

    private static void writePhotos(List<Part> fileParts, int nameId) throws IOException {
        int tmpNameIdForFiles = nameId;
        for (Part filePart : fileParts) {
            filePart.write("D:\\IDEA projects\\TechnoGrad\\src\\main\\webapp\\static\\images\\" + tmpNameIdForFiles + ".jpg");
            tmpNameIdForFiles++;
        }
    }

}
