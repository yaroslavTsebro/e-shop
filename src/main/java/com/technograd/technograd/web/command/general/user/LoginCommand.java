package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Post;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.manager.category.CreateCategory;
import com.technograd.technograd.web.exeption.*;
import com.technograd.technograd.web.passwordSecurity.PasswordSecurityUtil;
import com.technograd.technograd.web.recaptcha.RecaptchaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class LoginCommand extends Command {

    private static final long serialVersionUID = 6274453467176873674L;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("Login command started");

        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        logger.trace("Request parameter : email -> " + email);

        String password = request.getParameter("password");
        logger.trace("Request parameter : password -> " + password);

        String errorMessage;
        String forward;
        boolean valid;

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "login.command.values.empty";
            logger.error("errorMessage --> " + "email/password cannot be empty");
            throw new AppException(errorMessage);
        }

        String gCaptchaResponse = request.getParameter("g-recaptcha-response");

        valid = RecaptchaUtil.verify(gCaptchaResponse);

        if (!valid) {
            errorMessage = "login.command.captcha.invalid";
            logger.error("errorMessage --> " + "Captcha isn`t valid");
            throw new AppException(errorMessage);
        }

        logger.debug("captcha is valid");

        User user;
        try {
            user = new UserDAO().getUserByEmail(email);
        } catch (DBException exception) {
            errorMessage = "user.dao.find.user.error";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }

        logger.trace("Found user at DB: user-> " + user);

        if (user == null || !PasswordSecurityUtil.verifyPassword(password, user.getPassword(), user.getSalt())) {
            errorMessage = "login.command.credentials.invalid";
            logger.error("errorMessage --> " + "email/password isn`t valid");
            throw new AppException(errorMessage);
        } else {
            Post userPost = user.getPost();
            logger.debug("User role --> " + userPost);
            session.setAttribute("user", user);
            logger.trace("Set the session attribute: user --> " + user);
            session.setAttribute("userPost", userPost);
            logger.info("User " + user + " logged as " + userPost.toString().toLowerCase());
            forward = Commands.VIEW_MENU_COMMAND;

            String userLocaleName = user.getLocaleName();
            logger.debug("userLocalName --> " + userLocaleName);
            if (userLocaleName != null && !userLocaleName.isEmpty()) {
                session.setAttribute("lang", userLocaleName);
                logger.debug("Set the session attribute: user locale Name -> " + userLocaleName);
            } else {
                session.setAttribute("lang", "en");
                logger.debug("Set the session attribute: default lang - en ");
            }

        }
        logger.debug("Login command finished");
        return forward;
    }
}
