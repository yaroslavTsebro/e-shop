package com.technograd.technograd.web.command.manager.intend;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.Commands;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.email.EmailUtility;
import com.technograd.technograd.web.exсeption.AppException;
import com.technograd.technograd.web.exсeption.DBException;
import com.technograd.technograd.web.localization.LocalizationUtils;
import com.technograd.technograd.web.xls.XLSXUtility;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class SendReport extends Command {

    private static final long serialVersionUID = -7342467024354633652L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String fileSrc = "C:\\Users\\User\\Desktop\\1.xls";
        ResourceBundle rb = LocalizationUtils.getCurrentRb(session);

        Date date1 = java.sql.Date.valueOf(request.getParameter("date1"));
        Date date2 = java.sql.Date.valueOf(request.getParameter("date2"));
        Timestamp newDate1 = new Timestamp(date1.getTime());
        newDate1.setHours(0);
        newDate1.setMinutes(0);
        newDate1.setSeconds(0);
        newDate1.setNanos(0);

        Timestamp newDate2 = new Timestamp(date2.getTime());
        newDate2.setHours(23);
        newDate2.setMinutes(59);
        newDate2.setSeconds(59);
        newDate2.setNanos(9);

        IntendDAO intendDAO = new IntendDAO();
        XLSXUtility xlsxUtility = new XLSXUtility();
            try {
                File file = new File(fileSrc);
                file.createNewFile();
                List<Intend> intendList =  intendDAO.findAllReceivingsForReport(newDate1, newDate2);
                xlsxUtility.writeIntendsInXLS(intendList, fileSrc, rb);
                EmailUtility.sendMail(user.getEmail(), fileSrc, rb);
                file.delete();
                String userMessage = "send.report.success";
                session.setAttribute("userMessage", userMessage);
            } catch (DBException | InvalidFormatException | MessagingException e) {
                String errorMessage = "send.report.error";
                session.setAttribute("errorMessage", errorMessage);
                return request.getContextPath() + Commands.VIEW_REPORT_COMMAND;
            }
            return request.getContextPath() + Commands.VIEW_REPORT_COMMAND;
    }
}
