package com.technograd.technograd.web.command.manager.characteristic;

import com.technograd.technograd.dao.CharacteristicDAO;
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

public class DeleteCharacteristic extends Command {

    private static final long serialVersionUID = 2585592730326793675L;
    private static final Logger logger = LogManager.getLogger(DeleteCharacteristic.class.getName());

    private final CharacteristicDAO characteristicDAO;

    public DeleteCharacteristic(CharacteristicDAO characteristicDAO) {
        this.characteristicDAO = characteristicDAO;
    }

    public DeleteCharacteristic() {
        this.characteristicDAO = new CharacteristicDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("DeleteCharacteristic execute started");
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("delete_by_id"));
        logger.trace("delete_by_id ->" + id);
        try{
            characteristicDAO.deleteCharacteristicById(id);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.characteristic.delete";
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + "controller?command=viewCharacteristics";
        }

        logger.info("DeleteCharacteristic execute finished, path transferred to controller");
        return request.getContextPath() + "controller?command=viewCharacteristics";
    }
}
