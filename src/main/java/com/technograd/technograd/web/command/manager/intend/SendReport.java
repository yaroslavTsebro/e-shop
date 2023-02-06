package com.technograd.technograd.web.command.manager.intend;

import com.technograd.technograd.Path;
import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.dao.entity.User;
import com.technograd.technograd.web.command.Command;
import com.technograd.technograd.web.email.EmailUtility;
import com.technograd.technograd.web.exeption.AppException;
import com.technograd.technograd.web.exeption.DBException;
import com.technograd.technograd.web.localization.LocalizationUtils;
import com.technograd.technograd.web.xlsx.XLSXUtility;
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

        Date daily = java.sql.Date.valueOf(request.getParameter("daily"));
        Timestamp date1 = new Timestamp(daily.getTime());

        Timestamp date2 = new Timestamp(daily.getTime());
        date1.setHours(23);
        date1.setMinutes(59);
        date1.setSeconds(59);
        date1.setNanos(9);
        IntendDAO intendDAO = new IntendDAO();
        XLSXUtility xlsxUtility = new XLSXUtility();
            try {
                File file = new File(fileSrc);
                file.createNewFile();
                List<Intend> intendList =  intendDAO.findAllReceivingsForReport(date1, date2);
                xlsxUtility.writeIntendsInXLS(intendList, fileSrc, rb);
                EmailUtility.sendMail(user.getEmail(), fileSrc, rb);
            } catch (DBException | InvalidFormatException | MessagingException e) {
                throw new RuntimeException(e);
            }
            return Path.REPORT_PAGE;
    }
}
