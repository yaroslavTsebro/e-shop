package com.technograd.technograd.web.command;

import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class ChangeLanguage extends Command {

    private static final long serialVersionUID = 5604700245317955539L;
    private static final Logger logger = LogManager.getLogger(ChangeLanguage.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("Change language command is started");

        HttpSession session = request.getSession();

        String queryString = request.getParameter("queryString");
        if(queryString == null || queryString.isEmpty()){
            queryString = "command=viewMenu";
        }

        String language = request.getParameter("language");

        session.setAttribute("lang", language);
        logger.trace("Set session attribute lang =>" + language);

        User user = (User) session.getAttribute("user");
        if(user != null){
            try{
                if(language.equals("ua")){
                    UserDAO.updateUserLanguageToUa(user.getId());
                } else if (language.equals("en")) {
                    UserDAO.updateUserLanguageToEn(user.getId());
                }
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }

        String forward = request.getContextPath() + request.getServletPath() + "?" + queryString;
        logger.debug("Change language command is finished");
        return forward;
    }
}
