package com.technograd.technograd.web.email;

import com.technograd.technograd.web.command.manager.characteristic.CreateCharacteristic;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;


public class EmailUtility {
    private static final Logger logger = LogManager.getLogger(CreateCharacteristic.class.getName());

    public EmailUtility() {}

    public static void sendPasswordLink(String recipientMail, String code, ResourceBundle rb) throws MessagingException {
        logger.debug("sendMail method is started");

        Properties properties = new Properties();
        try {
            properties.load(EmailUtility.class.getClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException exception) {
            logger.error("An error has occurred while retrieving data from email.properties");
            throw new MessagingException();
        }

        System.out.println(properties.getProperty("mail.login"));
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.login"), properties.getProperty("mail.password"));
            }
        });

        logger.debug("Received email session => " + session);
        session.setDebug(false);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(properties.getProperty("mail.login")));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientMail));
        message.setSubject(rb.getString("verify.password.change.email.title"), "UTF-8");
        String form = null;
        try{
            form = "<table align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" style=\"border-collapse:collapse;width:621px;color:#ffffff;background:#fdfdfd\">\n" +
                    "\t<tbody>\n" +
                    "\t<tr>\n" +
                    "\t\t<td style=\"padding:0 40px 80px;font-size:10pt;color:#262222;font-family:tahoma,geneva,sans-serif\">\n" +
                    "\t\t\t<p style=\"color:#262222\">" + rb.getString("verify.password.change.email.body.title") + " " + code
                    + "</p>\n" +
                    "\t\t\t<p style=\"color:#262222\">\n" +
                    "\t\t\t<form action=\"http:/localhost:8080/controller\" method=\"get\">\n" +
                    "\t\t\t<input type=\"hidden\" name=\"command\" value=\"changePasswordCommand\"/>\n" +
                    "\t\t\t<input type=\"hidden\" name=\"email\" value=\"" + recipientMail+ "\"/>\n" +
                    "\t\t\t<input type=\"hidden\" name=\"code\" value=\"" + code
                    + "\"/>\n" +
                    "\t\t\t<input type=submit style=\"background-color: #45c0d5;color: white;padding: 16px 20px;margin: 8px 0;border: none;cursor: pointer;width: 100%;opacity: 0.9;\" value=\""
                    + rb.getString("verify.password.change.email.body.button") + "\"/>\n" +
                    "\t\t\t</form>\n" +
                    "\t\t\t</p>\n" +
                    "\t\t</td>\n" +
                    "\t</tr>\n" +
                    "\t</tbody>\n" +
                    "</table>";
        } catch (Exception e){
            System.out.println(e);
        }

        message.setContent(form, "text/html; charset=UTF-8");

        logger.debug("Sending email ... ");
        Transport.send(message);
        logger.debug("Email sent successfully, sendMail method finished his work");
    }

    public static void sendMail(String recipientMail, String filePath, ResourceBundle rb) throws MessagingException {
        logger.debug("sendMail method is started");

        Properties properties = new Properties();
        try {
            properties.load(EmailUtility.class.getClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException exception) {
            logger.error("An error has occurred while retrieving data from email.properties");
            throw new MessagingException();
        }

        System.out.println(properties.getProperty("mail.login"));
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.login"), properties.getProperty("mail.password"));
            }
        });

        logger.debug("Received email session => " + session);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(properties.getProperty("mail.login")));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientMail));

        message.setSubject(rb.getString("weekly.report.name"), "UTF-8");

        Multipart multipart = new MimeMultipart();
        MimeBodyPart attachment = new MimeBodyPart();
        MimeBodyPart text = new MimeBodyPart();

        try {
            File f = new File(filePath);
            attachment.attachFile(f);
            text.setText(rb.getString("send.report.email.text"), "UTF-8");
            multipart.addBodyPart(text);
            multipart.addBodyPart(attachment);
        } catch (IOException exception) {
            logger.error("An error has occurred while getting the report file : " + exception.getMessage());
        }

        message.setContent(multipart, "text/html; charset=UTF-8");

        logger.debug("Sending email ... ");
        Transport.send(message);
        logger.debug("Email sent successfully, sendMail method finished his work");
    }
}
