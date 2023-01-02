package com.technograd.technograd.web.recaptcha;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class RecaptchaUtil {
    public static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET_KEY = "*****************************";
    public static final String SITE_KEY = "*****************************";
    
    public static boolean verify(String recaptchaResponse){

        if(recaptchaResponse == null || recaptchaResponse.length() == 0){
            return false;
        }

        try{
            URL verifyUrl = new URL(RECAPTCHA_URL);
            HttpsURLConnection connection = (HttpsURLConnection) verifyUrl.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String params = "secret=" + SECRET_KEY + "&response=" + recaptchaResponse;

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes());

            outputStream.flush();
            outputStream.close();

            int code = connection.getResponseCode();
            InputStream stream = connection.getInputStream();

            JsonReader reader = Json.createReader(stream);
            JsonObject object = reader.readObject();
            reader.close();
            return object.getBoolean("success");
        } catch(Exception e){
            return false;
        }
    }
}
