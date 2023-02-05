package com.technograd.technograd.web.command.manager.intend.receiving;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.CategoryDAO;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.manager.category.CreateCategory;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        List<Intend> receivingList = null;
        try {
            receivingList = intendDAO.findAllReceivings();
            logger.trace("receivingList ->" + receivingList);
        } catch (DBException exception) {
            throw new AppException(exception.getMessage());
        } finally {
            request.setAttribute("receivingList", receivingList);
        }
        logger.info("ViewReceiving execute finished, path transferred to controller");
        return Path.SENDING_PAGE;
    }
}
