package com.technograd.technograd.web.command.manager.characteristic;

import com.technograd.technograd.Path;
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
import java.util.List;

public class ViewCharacteristic extends Command {

    private static final long serialVersionUID = 8389809346058200398L;
    private static final Logger logger = LogManager.getLogger(ViewCharacteristic.class.getName());

    private final CharacteristicDAO characteristicDAO;

    public ViewCharacteristic(CharacteristicDAO characteristicDAO) {
        this.characteristicDAO = characteristicDAO;
    }

    public ViewCharacteristic() {
        this.characteristicDAO = new CharacteristicDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewCharacteristic execute started");
        HttpSession session = request.getSession();

        List<Characteristic> characteristicList = null;
        try {
            characteristicList = characteristicDAO.getAllCharacteristics();
            logger.trace("characteristicList ->" + characteristicList);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.characteristic.view";
            session.setAttribute("errorMessage", errorMessage);
            return Path.CHARACTERISTIC_PAGE;
        } finally {
            request.setAttribute("characteristicList", characteristicList);
        }
        logger.info("ViewCharacteristic execute finished, path transferred to controller");
        return Path.CHARACTERISTIC_PAGE;
    }
}
