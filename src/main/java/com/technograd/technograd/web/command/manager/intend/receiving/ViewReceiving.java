package com.technograd.technograd.web.command.manager.intend.receiving;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
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

public class ViewReceiving extends Command {

    private static final long serialVersionUID = 8160910644975538045L;
    private static final Logger logger = LogManager.getLogger(ViewReceiving.class.getName());
    private final IntendDAO intendDAO;

    public ViewReceiving() {
        this.intendDAO = new IntendDAO();
    }

    public ViewReceiving(IntendDAO intendDAO) {
        this.intendDAO = intendDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewReceiving execute started");
        HttpSession session = request.getSession();

        List<Intend> receivingList = null;
        try {
            receivingList = intendDAO.findAllReceivings();
            logger.trace("receivingList ->" + receivingList);
        } catch (DBException e) {
            logger.trace("error ->" + e);
            String errorMessage = "error.receiving.view";
            session.setAttribute("errorMessage", errorMessage);
            return Path.ADMIN_PANEL;
        } finally {
            request.setAttribute("receivingList", receivingList);
        }
        logger.info("ViewReceiving execute finished, path transferred to controller");
        return Path.SENDING_PAGE;
    }
}
