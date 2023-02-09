package com.technograd.technograd.web.command.customer.profile;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.ChangeLanguage;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import com.technograd.technograd.web.passwordSecurity.PasswordSecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ChangePasswordCommand extends Command {
    private static final long serialVersionUID = -4299858300032417356L;
    private static final Logger logger = LogManager.getLogger(ChangeLanguage.class.getName());
    private final UserDAO userDao;

    public ChangePasswordCommand() {
        this.userDao = new UserDAO();
    }

    public ChangePasswordCommand(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("Change password command is started");

        String forward = null;

        logger.debug("Request method => " + request.getMethod());
        if (request.getMethod().equals("GET")) {
            forward = doGet(request);
        } else {
            if (request.getMethod().equals("POST")) {
                forward = doPost(request);
            }
        }
        if (forward == null) {
            forward = Path.MENU_PAGE;
        }

        logger.debug("Change password command is finished");
        return forward;
    }

    private String doPost(HttpServletRequest request) throws AppException {
        logger.debug("Change password command is started at the post method");

        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        String newPass = request.getParameter("new_password");
        String repNewPass = request.getParameter("rep_password");

        logger.trace("email ->" + email);

        User currentUser;
        try {
            currentUser = userDao.getUserByEmail(email);
        } catch (DBException exception) {
            String errorMessage = "user.dao.find.user.error";
            logger.error("errorMessage --> " + exception);
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_REGISTER_PAGE;
        }

        if (currentUser == null ) {
            String errorMessage = "change.password.command.old.password.mismatch";
            logger.error("errorMessage => user entered invalid old password ");
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_REGISTER_PAGE;
        }

        try{
            String userCode = userDao.getCode(currentUser.getId());
            String userSalt = userDao.getSalt(currentUser.getId());
            if (!PasswordSecurityUtil.verifyPassword(userCode, code, userSalt)) {
                String errorMessage = "change.password.link.invalid";
                logger.error("Invalid link");
                session.setAttribute("errorMessage", errorMessage);
                return request.getContextPath() + Commands.VIEW_REGISTER_PAGE;
            }
        } catch (DBException exception) {
            String errorMessage = "user.dao.find.user.error";
            logger.error("errorMessage --> " + exception);
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_REGISTER_PAGE;
        }


        if (newPass == null || !newPass.equals(repNewPass)) {
            String errorMessage = "change.password.command.new.password.rep.isn`t.equal";
            logger.error("errorMessage -> New password is not equal to repeat new password");
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_REGISTER_PAGE;
        }

        String newSalt = PasswordSecurityUtil.getSalt(50);
        String newSecurePassword = PasswordSecurityUtil.generateSecurePassword(newPass, newSalt);

        try {
            userDao.updateUserPassword(newSecurePassword, newSalt, currentUser.getId());
        } catch (DBException exception) {
            String errorMessage = "change.password.update.password.error";
            logger.error("errorMessage --> " + exception);
            session.setAttribute("errorMessage", errorMessage);
            return request.getContextPath() + Commands.VIEW_REGISTER_PAGE;
        }

        logger.debug(newPass + repNewPass);

        session.setAttribute("userMessage", "change.password.successfully.message");
        return Commands.VIEW_LOGIN_PAGE;
    }

    private String doGet(HttpServletRequest request) throws AppException {
        logger.debug("Change password command is started at the get method");

        String email = request.getParameter("email");
        logger.trace("email ->" + email);

        User currentUser;
        try {
            currentUser = userDao.getUserByEmail(email);
        } catch (DBException exception) {
            String errorMessage = "user.dao.find.user.error";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }
        String parameter = request.getParameter("code");

        if (parameter == null || parameter.isEmpty()) {
            String errorMessage = "change.password.link.invalid";
            logger.error("Invalid credentials");
            throw new AppException(errorMessage);
        }

        String userCode;
        String userSalt;
        try {
            userCode = userDao.getCode(currentUser.getId());
            userSalt = userDao.getSalt(currentUser.getId());
        } catch (DBException exception) {
            String errorMessage = "error.occurred";
            logger.error("An error has occurred while retrieving code data");
            throw new AppException(errorMessage);
        }

        if (userCode == null || userCode.isEmpty() || userSalt == null || userSalt.isEmpty()) {
            String errorMessage = "change.password.link.invalid";
            logger.error("Invalid credentials");
            throw new AppException(errorMessage);
        }

        if (!PasswordSecurityUtil.verifyPassword(userCode,parameter , userSalt)) {
            String errorMessage = "change.password.link.invalid";
            logger.error("Invalid link");
            throw new AppException(errorMessage);
        }

        return Path.CHANGE_PASSWORD_PAGE;
    }
}
