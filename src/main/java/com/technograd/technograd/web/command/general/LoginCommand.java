package com.technograd.technograd.web.command.general;

import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Post;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.error.AppException;
import com.technograd.technograd.web.error.DBException;
import com.technograd.technograd.web.passwordSecurity.PasswordSecurityUtil;
import com.technograd.technograd.web.recaptcha.RecaptchaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginCommand extends Command {

    private static final long serialVersionUID = 6274453467176873674L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorMessage;
        String forward;
        boolean valid;

        if(email == null || password == null || email.isEmpty() || password.isEmpty() ){
            errorMessage = "login.email.or.password.is.empty";
            throw new AppException(errorMessage);
        }

        String recaptchaResponse = request.getParameter("g-recaptcha-response");
        valid = RecaptchaUtil.verify(recaptchaResponse);

        if (!valid){
            errorMessage = "login.captcha.invalid";
            throw new AppException(errorMessage);
        }

        User user;
        try{
            user = new UserDAO().getUserByEmail(email);
        } catch (DBException e) {
            errorMessage = "user.dao.find.by.email.error";
            throw new AppException(errorMessage);
        }
        if(user == null
                || !PasswordSecurityUtil.verifyPassword(password, user.getPassword(), user.getSalt())){
            errorMessage = "login.command.credentials.invalid";
            throw new AppException(errorMessage);
        } else {
            Post userPost = user.getPost();
            session.setAttribute("user", user);
            session.setAttribute("userPost", userPost);
            forward = Commands.VIEW_MENU_COMMAND;

            String userLocaleName = user.getLocaleName();
            if(userLocaleName != null && !userLocaleName.isEmpty()){
                session.setAttribute("lang", userLocaleName);
            } else {
                session.setAttribute("lang", "en");
            }
        }
        return forward;
    }
}
