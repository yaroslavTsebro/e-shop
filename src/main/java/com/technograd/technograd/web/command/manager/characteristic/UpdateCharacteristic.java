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
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class UpdateCharacteristic extends Command {

    private static final long serialVersionUID = 1108101221128289750L;
    private static final Logger logger = LogManager.getLogger(UpdateCharacteristic.class.getName());

    private final CharacteristicDAO characteristicDAO;

    public UpdateCharacteristic(CharacteristicDAO characteristicDAO) {
        this.characteristicDAO = characteristicDAO;
    }

    public UpdateCharacteristic() {
        this.characteristicDAO = new CharacteristicDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("UpdateCharacteristic execute started");
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("update_by_id"));
        logger.trace("update_by_id ->" + id);
        String nameUa = request.getParameter("updated_name_ua");
        logger.trace("updated_name_ua ->" + nameUa);
        String nameEn = request.getParameter("updated_name_en");
        logger.trace("updated_name_en ->" + nameEn);

        Characteristic characteristic = new Characteristic(id ,nameUa, nameEn);
        try {
            characteristicDAO.updateCharacteristic(characteristic);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.characteristic.update";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCharacteristics";
        }
        logger.info("UpdateCharacteristic execute finished, path transferred to controller");
        return request.getContextPath() + "/controller?command=viewCharacteristics";
    }
}
