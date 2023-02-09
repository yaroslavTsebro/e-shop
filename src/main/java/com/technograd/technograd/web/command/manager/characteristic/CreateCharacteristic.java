package com.technograd.technograd.web.command.manager.characteristic;

import com.technograd.technograd.dao.CharacteristicDAO;
import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class CreateCharacteristic extends Command {

    private static final long serialVersionUID = -5068493456362968676L;
    private static final Logger logger = LogManager.getLogger(CreateCharacteristic.class.getName());
    private final CharacteristicDAO characteristicDAO;

    public CreateCharacteristic(CharacteristicDAO characteristicDAO) {
        this.characteristicDAO = characteristicDAO;
    }

    public CreateCharacteristic() {
        this.characteristicDAO = new CharacteristicDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("CreateCharacteristic execute started");
        HttpSession session = request.getSession();

        String nameUa = request.getParameter("name_ua");
        logger.trace("name_ua ->" + nameUa);
        String nameEn = request.getParameter("name_en");
        logger.trace("name_en ->" + nameEn);

        Characteristic characteristic = new Characteristic(nameUa, nameEn);
        try {
            characteristicDAO.createCharacteristic(characteristic);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.characteristic.create";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCharacteristics";
        }
        logger.info("CreateCharacteristic execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCharacteristics";
    }
}
