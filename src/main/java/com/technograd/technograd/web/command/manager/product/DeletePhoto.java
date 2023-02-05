package com.technograd.technograd.web.command.manager.product;

import com.technograd.technograd.dao.PhotoDAO;
import com.technograd.technograd.dao.ProductDAO;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class DeletePhoto extends Command {

    private static final long serialVersionUID = -383776128562777486L;
    private static final Logger logger = LogManager.getLogger(DeletePhoto.class.getName());

    private final PhotoDAO photoDAO;

    public DeletePhoto(PhotoDAO photoDAO) {
        this.photoDAO = photoDAO;
    }

    public DeletePhoto() {
        this.photoDAO = new PhotoDAO();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, AppException {
        int id = Integer.parseInt(req.getParameter("delete_photo_id"));
        logger.trace("delete_photo_id ->" + id);
        String name;
        try {
            name = photoDAO.deletePhotoById(id);
        } catch (DBException e) {
            throw new AppException();
        }
        try{
            File f= new File("src/main/webapp/static/images" + name + ".jpg");
            if(!f.delete()){
                throw new Exception();
            }
        } catch (Exception e){
            throw new AppException(e);
        }

        return req.getContextPath() + "/product";
    }
}
