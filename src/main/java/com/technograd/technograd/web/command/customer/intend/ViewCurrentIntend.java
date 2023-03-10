package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.IntendReturnDAO;
import com.technograd.technograd.dao.entity.Condition;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.IntendReturn;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ViewCurrentIntend extends Command {
    private static final long serialVersionUID = -4235651235160596309L;
    private final IntendReturnDAO intendReturnDAO;
    private final IntendDAO intendDAO;

    public ViewCurrentIntend() {
        this.intendReturnDAO = new IntendReturnDAO();
        this.intendDAO = new IntendDAO();
    }

    public ViewCurrentIntend(IntendReturnDAO intendReturnDAO, IntendDAO intendDAO) {
        this.intendReturnDAO = intendReturnDAO;
        this.intendDAO = intendDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        int id = Integer.parseInt(request.getParameter("id"));
        Intend intend;
        try{
            intend = intendDAO.findIntendById(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        if(intend.getUserId() != userId){
            throw new AppException();
        }
        IntendReturn intendReturn = null;
        if(intend.getCondition().equals(Condition.TURNED_BACK)){
            try {
                intendReturn = intendReturnDAO.findIntendReturnByIntendId(intend.getId());
            } catch (DBException e) {
                throw new RuntimeException(e);
            } finally {
                request.setAttribute("intendReturn", intendReturn);
            }
        }

        request.setAttribute("intend", intend);
        return Path.CURRENT_INTEND_PAGE;
    }
}
