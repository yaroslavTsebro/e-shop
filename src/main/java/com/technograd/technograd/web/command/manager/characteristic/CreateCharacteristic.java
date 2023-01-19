package com.technograd.technograd.web.command.manager.characteristic;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CharacteristicDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class CreateCharacteristic extends Command {

    private static final long serialVersionUID = -5068493456362968676L;
    private static final Logger logger = LogManager.getLogger(CreateCharacteristic.class.getName());


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("CreateCharacteristic execute started");
        String nameUa = request.getParameter("name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = request.getParameter("name_en");
        logger.trace("name_en ->" + nameEn);

        Characteristic characteristic = new Characteristic(nameUa, nameEn);
        try {
            CharacteristicDAO.createCharacteristic(characteristic);
        } catch (DBException e) {
            logger.error("errorMessage --> " + e);
            throw new AppException(e);
        }
        logger.info("CreateCharacteristic execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCharacteristics";
    }
}
