package com.technograd.technograd.web.command.manager.characteristic;

import com.technograd.technograd.Path;
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
import java.util.List;

public class ViewCharacteristic extends Command {

    private static final long serialVersionUID = 8389809346058200398L;
    private static final Logger logger = LogManager.getLogger(ViewCharacteristic.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewCharacteristic execute started");

        List<Characteristic> characteristicList = null;
        try {
            characteristicList = CharacteristicDAO.getAllCharacteristics();
            logger.trace("characteristicList ->" + characteristicList);
        } catch (DBException exception) {
            try {
                throw new AppException(exception.getMessage());
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        } finally {
            request.setAttribute("characteristicList", characteristicList);
        }
        logger.info("ViewCharacteristic execute finished, path transferred to controller");
        return Path.CHARACTERISTIC_PAGE;
    }
}
