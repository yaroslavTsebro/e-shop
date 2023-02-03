package com.technograd.technograd.web.command.customer.intend;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
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
import java.util.List;

public class ViewProfilePage extends Command {
    private static final long serialVersionUID = -3088854743984898954L;

    private static final Logger logger = LogManager.getLogger(ViewProfilePage.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getId();
        logger.trace("id ->" + id);

        List<Intend> intendList = null;
        try{
            intendList = IntendDAO.findIntendByUserId(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        } finally {
            request.setAttribute("intendList", intendList);
        }

        logger.info("ViewSending execute finished, path transferred to controller");
        return Path.PROFILE_PAGE;
    }
}
