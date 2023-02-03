package com.technograd.technograd.web.command.customer.profile;

import com.technograd.technograd.dao.UserDAO;
import com.technograd.technograd.dao.entity.Post;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.ChangeLanguage;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.email.EmailUtility;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import com.technograd.technograd.web.localization.LocalizationUtils;
import com.technograd.technograd.web.passwordSecurity.PasswordSecurityUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ResourceBundle;

public class SendConfirmationLink extends Command {
    private static final long serialVersionUID = 3606547140122058854L;
    private static final Logger logger = LogManager.getLogger(ChangeLanguage.class.getName());
    private static final int CODE_LENGTH = 20;
    private static final int SALT_LENGTH = 50;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        logger.debug("Send confirmation link command is started");
        HttpSession session = request.getSession();
        ResourceBundle rb = LocalizationUtils.getCurrentRb(session);

        String email = request.getParameter("email");
        logger.trace("email ->" + email);

        User currentUser;
        try {
            currentUser = new UserDAO().getUserByEmail(email);
            if(currentUser.getPost().equals(Post.MANAGER)){
                throw new AppException();
            }
        } catch (DBException exception) {
            String errorMessage = "user.dao.find.user.error";
            logger.error("errorMessage --> " + exception);
            throw new AppException(errorMessage);
        }

        String code = generateCode(currentUser.getId());
        sendConfirmationCode(currentUser, code, rb);

        session.setAttribute("userMessage", "send.confirmation.link.command");
        logger.debug("Send confirmation link command is finished");
        return Commands.VIEW_LOGIN_PAGE;
    }

    private void sendConfirmationCode(User user, String link, ResourceBundle rb) throws AppException {
        try {
            EmailUtility.sendPasswordLink(user.getEmail(), link, rb);
        } catch (MessagingException exception) {
            String errorMessage = "send.confirmation.message.error";
            logger.error("An error occurred while sending confirmation message");
            throw new AppException(errorMessage);
        }
    }

    private String generateCode(int userId) throws AppException {
        String randomCode = PasswordSecurityUtil.getSalt(CODE_LENGTH);
        String saltRandomCode = PasswordSecurityUtil.getSalt(SALT_LENGTH);
        try {
            new UserDAO().addConfirmationCode(userId, saltRandomCode, randomCode);
        } catch (DBException exception) {
            String errorMessage = "send.confirmation.message.get.code.error";
            logger.error("An error has occurred, maybe message is already sent, check your email");
            throw new AppException(errorMessage);
        }
        return PasswordSecurityUtil.generateSecurePassword(randomCode, saltRandomCode);
    }
}
