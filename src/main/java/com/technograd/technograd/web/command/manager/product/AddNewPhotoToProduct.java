package com.technograd.technograd.web.command.manager.product;

import com.technograd.technograd.dao.PhotoDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.dao.entity.Photo;
import com.technograd.technograd.dao.entity.Product;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@MultipartConfig(   fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "updateProduct", value = "/update/product")
public class AddNewPhotoToProduct extends HttpServlet {
    private static final long serialVersionUID = -4935177126744902197L;
    private static final Logger logger = LogManager.getLogger(AddNewPhotoToProduct.class.getName());

    private final ProductDAO productDAO;
    private final PhotoDAO photoDAO;

    public AddNewPhotoToProduct(ProductDAO productDAO, PhotoDAO photoDAO) {
        this.productDAO = productDAO;
        this.photoDAO = photoDAO;
    }

    public AddNewPhotoToProduct() {
        this.productDAO = new ProductDAO();
        this.photoDAO = new PhotoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("product_id"));
        logger.trace("product_id ->" + id);
        HttpSession session = req.getSession();

        List<Part> fileParts = req.getParts().stream().filter(part -> "new_files".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());

        List<Photo> photos = new ArrayList<>();

        try{
            Product product = productDAO.getProductById(id);
        } catch (DBException e){
        }

        int nameId = 0;
        try {
            nameId = photoDAO.getNextId();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        if(nameId == -1){
            throw new RuntimeException();
        }

        int tmpNameId = nameId;
        for (Part filePart : fileParts) {
            photos.add(new Photo(Integer. toString(tmpNameId), id));
            tmpNameId++;
        }

        try{
            photoDAO.insertListOfPhotos(photos);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        writePhotos(fileParts, nameId);
        resp.getWriter().print("The files uploaded successfully.");
    }

    private static void writePhotos(List<Part> fileParts, int nameId) throws IOException {
        int tmpNameIdForFiles = nameId;
        for (Part filePart : fileParts) {
            //filePart.write("C:\\Users\\User\\Desktop\\" + tmpNameIdForFiles + ".jpg");
            filePart.write("src/main/webapp/static/images" + tmpNameIdForFiles + ".jpg");
            tmpNameIdForFiles++;
        }
    }
}
