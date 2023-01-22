package com.technograd.technograd.web.command.manager.characteristic;

import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.CharacteristicDAO;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DeleteCharacteristic extends Command {

    private static final long serialVersionUID = 2585592730326793675L;
    private static final Logger logger = LogManager.getLogger(DeleteCharacteristic.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("DeleteCharacteristic execute started");

        int id = Integer.parseInt(request.getParameter("delete_by_id"));
        logger.trace("delete_by_id ->" + id);
        try{
            CharacteristicDAO.deleteCharacteristicById(id);
        } catch (DBException e) {
            logger.error("errorMessage --> " + e);
            throw new AppException(e);
        }

        logger.info("DeleteCharacteristic execute finished, path transferred to controller");
        return request.getContextPath() + "controller?command=viewCharacteristics";
    }
}