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

public class SearchCharacteristic extends Command {

    private static final long serialVersionUID = 4918863306195670390L;
    private static final Logger logger = LogManager.getLogger(SearchCharacteristic.class.getName());

    private final CharacteristicDAO characteristicDAO;

    public SearchCharacteristic(CharacteristicDAO characteristicDAO) {
        this.characteristicDAO = characteristicDAO;
    }

    public SearchCharacteristic() {
        this.characteristicDAO = new CharacteristicDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("SearchCharacteristic execute started");
        HttpSession session = request.getSession();

        String pattern = request.getParameter("pattern");
        if (pattern == null || pattern.isEmpty()) {
            return request.getContextPath() + "/controller?command=viewCharacteristics";
        }
        logger.debug("Pattern is => " + pattern);
        List<Characteristic> characteristicList = null;
        try {
            characteristicList = characteristicDAO.searchCharacteristic(pattern);
            logger.trace("characteristicList ->" + characteristicList);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.characteristic.search";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCharacteristics";
        } finally {
            request.setAttribute("characteristicList", characteristicList);
        }
        logger.info("SearchCharacteristic execute finished, path transferred to controller");
        return Path.CHARACTERISTIC_PAGE;
    }
}
