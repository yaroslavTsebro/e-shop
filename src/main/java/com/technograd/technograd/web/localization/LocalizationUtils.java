package com.technograd.technograd.web.localization;

import jakarta.servlet.http.HttpSession;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationUtils {

    private LocalizationUtils() {}

    public static ResourceBundle getCurrentRb(HttpSession session) {

        String localeName = "en";
        Object localeObj = session.getAttribute("lang");
        if (localeObj != null) {
            localeName = localeObj.toString();
        }

        Locale locale;
        if ("ua".equals(localeName)) {
            locale = new Locale("ua", "UA");
        } else {
            locale = new Locale("en", "EN");
        }
        return ResourceBundle.getBundle("resources", locale);
    }

    public static ResourceBundle getEnglishRb() {
        Locale locale = new Locale("en", "EN");
        return ResourceBundle.getBundle("resources", locale);
    }

}