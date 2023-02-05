package com.technograd.technograd.web.command.general.user;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Post;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.command.manager.category.CreateCategory;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import com.technograd.technograd.web.passwordSecurity.PasswordSecurityUtil;
import com.technograd.technograd.web.recaptcha.RecaptchaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class RegisterCommand extends Command {

    private static final long serialVersionUID = 2258852763044653334L;
    private static final Logger logger = LogManager.getLogger(CreateCategory.class.getName());

    private final UserDAO userDAO;

    public RegisterCommand(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public RegisterCommand() {
        this.userDAO = new UserDAO();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("Register command started");

        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        logger.trace("Request parameter : email -> " + email);
        String password = request.getParameter("password");
        logger.trace("Request parameter : password -> " + password);
        String name = request.getParameter("name");
        logger.trace("Request parameter : name -> " + name);
        String lastname = request.getParameter("lastname");
        logger.trace("Request parameter : lastname -> " + lastname);

        String errorMessage;
        String forward;
        boolean valid;

        if(checkCredentials(email, password, name, lastname)){
            errorMessage = "register.command.values.empty";
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

        User user = null;
        try {
            user = userDAO.getUserByEmail(email);
        } catch (Exception ignored) {

        }

        logger.trace("Found user at DB: user-> " + user);

        if(user != null){
            errorMessage = "login.email.command.already.used";
            logger.error("errorMessage --> " + "This Email have used");
            throw new AppException(errorMessage);
        } else {
            User newUser = new User();
            String salt = PasswordSecurityUtil.getSalt(50);
            String hashedPass =  PasswordSecurityUtil.generateSecurePassword(password, salt);
            newUser.setLastname(lastname);
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(hashedPass);
            newUser.setSalt(salt);
            forward = request.getContextPath() + "/controller?command=loginPage";
            try {
                userDAO.createUser(newUser);
            } catch (DBException exception) {
                errorMessage = "user.dao.find.user.error";
                logger.error("errorMessage --> " + exception);
                throw new AppException(errorMessage);
            }
        }
        logger.debug("Login command finished");
        return forward;
    }

    private boolean checkCredentials(String email, String password, String name, String lastname ){
        return email == null || password == null || email.isEmpty() || password.isEmpty()
                || name == null || lastname == null || name.isEmpty() || lastname.isEmpty();
    }

}
