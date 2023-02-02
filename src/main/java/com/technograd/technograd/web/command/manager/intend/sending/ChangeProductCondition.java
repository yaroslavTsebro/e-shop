package com.technograd.technograd.web.command.manager.intend.sending;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Condition;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
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


public class ChangeProductCondition extends Command {

    private static final long serialVersionUID = -7068520945582040957L;

    private static final Logger logger = LogManager.getLogger(ChangeProductCondition.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        String oldCondition = request.getParameter("old_conditional");
        logger.trace("old_conditional ->" + oldCondition);

        String newCondition = request.getParameter("new_conditional");
        logger.trace("new_conditional ->" + newCondition);

        int intendId = Integer.parseInt(request.getParameter("intend_id"));
        logger.trace("intend_id ->" + intendId);

        checkCondition(oldCondition);
        checkCondition(newCondition);
        checkUser(id, user);
        checkIntend(intendId);

        if(oldCondition.equals(Condition.CART.toString())){

            throw new AppException();
        } else if(oldCondition.equals(Condition.NEW.toString())){

            if(newCondition.equals(Condition.ACCEPTED.toString())){
                try {
                    String query = IntendDAO.buildUpdateConditionQuery(newCondition);
                    IntendDAO.updateConditionAccepted(query, intendId);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            } else if(newCondition.equals(Condition.DENIED.toString())){
                try {
                    String query = IntendDAO.buildUpdateConditionQuery(newCondition);
                    IntendDAO.updateCondition(query, intendId);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            }  else {
                throw new AppException();
            }
        } else if (oldCondition.equals(Condition.TURNED_BACK.toString())) {

            throw new AppException();
        } else if (oldCondition.equals(Condition.ACCEPTED.toString())) {

            if(newCondition.equals(Condition.IN_WAY.toString())){
                try {
                    String query = IntendDAO.buildUpdateConditionQuery(newCondition);
                    IntendDAO.updateCondition(query, intendId);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            } else if(newCondition.equals(Condition.TURNED_BACK.toString())){
                try {
                    String query = IntendDAO.buildUpdateConditionQuery(newCondition);
                    IntendDAO.updateConditionTurnedBack(query, intendId);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            }  else {
                throw new AppException();
            }
        } else if(oldCondition.equals(Condition.DENIED.toString())){

            throw new AppException();
        } else if(oldCondition.equals(Condition.IN_WAY.toString())){

            if(newCondition.equals(Condition.COMPLETED.toString())){
                try {
                    String query = IntendDAO.buildUpdateConditionQuery(newCondition);
                    IntendDAO.updateCondition(query, intendId);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            } else if(newCondition.equals(Condition.TURNED_BACK.toString())){
                try {
                    String query = IntendDAO.buildUpdateConditionQuery(newCondition);
                    IntendDAO.updateConditionTurnedBack(query, intendId);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            }  else {
                throw new AppException();
            }
        } else if(oldCondition.equals(Condition.COMPLETED.toString())){

            if(newCondition.equals(Condition.TURNED_BACK.toString())){
                try {
                    String query = IntendDAO.buildUpdateConditionQuery(newCondition);
                    IntendDAO.updateConditionTurnedBack(query, intendId);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new AppException();
            }
        }

        return request.getContextPath() + "/controller?command=viewCurrentSending&id=" + intendId + "";
    }

    public void checkCondition(String condition) throws AppException {
        if(condition.equals(Condition.CART.toString())){

        } else if (condition.equals(Condition.COMPLETED.toString())) {

        } else if (condition.equals(Condition.ACCEPTED.toString())) {

        } else if (condition.equals(Condition.IN_WAY.toString())) {

        } else if (condition.equals(Condition.DENIED.toString())) {

        } else if (condition.equals(Condition.NEW.toString())) {

        } else if (condition.equals(Condition.TURNED_BACK.toString())) {

        } else {
            throw new AppException();
        }
    }

    public void checkUser(int id, User user) throws AppException {
        User checkUser;
        try{
            checkUser = UserDAO.getUserById(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        if(user.getPost() != checkUser.getPost()){
            throw new AppException();
        }
    }

    public void checkIntend(int intendId) throws AppException {
        Intend intend;
        try{
            intend = IntendDAO.findIntendById(intendId);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        if(intend == null){
            throw new AppException();
        }
    }

}
