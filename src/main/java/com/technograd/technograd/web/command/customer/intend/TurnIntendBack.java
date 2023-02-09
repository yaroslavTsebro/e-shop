package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Condition;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
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

public class TurnIntendBack extends Command {

    private static final long serialVersionUID = 2547799794825488154L;

    private static final Logger logger = LogManager.getLogger(TurnIntendBack.class.getName());

    private final IntendDAO intendDAO;

    public TurnIntendBack(IntendDAO intendDAO) {
        this.intendDAO = intendDAO;
    }
    public TurnIntendBack() {
        this.intendDAO = new IntendDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        int intendId = Integer.parseInt(request.getParameter("intend_id"));
        logger.trace("intend_id ->" + intendId);
        String reason = request.getParameter("reason");
        logger.trace("reason ->" + reason);

        Intend intend;
        try {
            intend = intendDAO.findIntendById(intendId);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if(intend.getUserId() != id || intend.getCondition().equals(Condition.TURNED_BACK) || intend.getCondition().equals(Condition.CART)){
            throw new RuntimeException();
        }
        String query = intendDAO.buildUpdateConditionQuery(Condition.TURNED_BACK.toString());
        if(intend.getCondition().equals(Condition.NEW)){
            try {
                intendDAO.updateConditionTurnedBackFromUser(query, intendId, reason);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                intendDAO.updateConditionTurnedBackFromUserWithReason(query, intendId, reason);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }

        return request.getContextPath() + "/controller?command=viewCurrentIntend&id=" + intendId + "";
    }
}
