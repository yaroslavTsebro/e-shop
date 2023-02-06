package com.technograd.technograd.web.command.manager.intend.sending;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.IntendReturnDAO;
import com.technograd.technograd.dao.ListIntendDAO;
import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Condition;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.IntendReturn;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ViewCurrentSending extends Command {
    private static final long serialVersionUID = 8591695459490297254L;
    private final IntendDAO intendDAO;
    private final UserDAO userDAO;
    private final IntendReturnDAO intendReturnDAO;

    public ViewCurrentSending() {
        this.intendDAO = new IntendDAO();
        this.userDAO = new UserDAO();
        this.intendReturnDAO = new IntendReturnDAO();
    }

    public ViewCurrentSending(IntendDAO intendDAO, UserDAO userDAO, IntendReturnDAO intendReturnDAO) {
        this.intendDAO = intendDAO;
        this.userDAO = userDAO;
        this.intendReturnDAO = intendReturnDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();

        Intend intend;
        User user;
        try{
            intend = intendDAO.findIntendById(id);
        } catch (DBException e) {
            session.setAttribute("errorMessage", "sending.admin.view.intend");
            return Path.SENDING_PAGE;
        }
        try{
            user = userDAO.getReducedUserById(intend.getUserId());
        } catch (DBException e) {
            session.setAttribute("errorMessage", "sending.admin.view.user");
            return Path.SENDING_PAGE;
        }
        IntendReturn intendReturn = null;
        if(intend.getCondition().equals(Condition.TURNED_BACK)){
            try {
                intendReturn = intendReturnDAO.findIntendReturnByIntendId(intend.getId());
            } catch (DBException e) {
                session.setAttribute("errorMessage", "sending.admin.view.intendReturn");
                return Path.SENDING_PAGE;
            } finally {
                request.setAttribute("intendReturn", intendReturn);
            }
        }

        request.setAttribute("intend", intend);
        request.setAttribute("user", user);
        return Path.ADMIN_CURRENT_SENDING_PAGE;
    }
}
