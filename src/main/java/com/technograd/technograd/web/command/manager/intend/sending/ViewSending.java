package com.technograd.technograd.web.command.manager.intend.sending;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.SendingOrReceiving;
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
import java.util.List;

public class ViewSending extends Command {
    private static final Logger logger = LogManager.getLogger(ViewSending.class.getName());
    private static final long serialVersionUID = -2199127900032332087L;

    private final IntendDAO intendDAO;

    public ViewSending() {
        this.intendDAO = new IntendDAO();
    }

    public ViewSending(IntendDAO intendDAO) {
        this.intendDAO = intendDAO;
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.info("ViewSending execute started");
        HttpSession session = request.getSession();

        String condition = request.getParameter("condition");
        List<Intend> sendingList = null;
        try {
            String query = intendDAO.viewIntendsQueryBuilder(condition, SendingOrReceiving.SENDING.name());
            sendingList = intendDAO.findAllIntendsFormQueryBuilder(query);
            logger.trace("sendingList ->" + sendingList);
        } catch (DBException exception) {
            session.setAttribute("errorMessage", "sending.admin.view.sending");
            return Path.ADMIN_PANEL;
        } finally {
            request.setAttribute("sendingList", sendingList);
        }
        logger.info("ViewSending execute finished, path transferred to controller");
        return Path.SENDING_PAGE;
    }
}