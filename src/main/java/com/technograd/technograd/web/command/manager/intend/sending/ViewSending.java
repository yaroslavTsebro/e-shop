package com.technograd.technograd.web.command.manager.intend.sending;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
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

public class ViewSending extends Command {


    private static final Logger logger = LogManager.getLogger(ViewSending.class.getName());
    private static final long serialVersionUID = -2199127900032332087L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewSending execute started");

        List<Intend> sendingList = null;
        try {
            sendingList = IntendDAO.findAllSendings();
            logger.trace("sendingList ->" + sendingList);
        } catch (DBException exception) {
            try {
                throw new AppException(exception.getMessage());
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        } finally {
            request.setAttribute("sendingList", sendingList);
        }
        logger.info("ViewSending execute finished, path transferred to controller");
        return Path.SENDING_PAGE;
    }
}