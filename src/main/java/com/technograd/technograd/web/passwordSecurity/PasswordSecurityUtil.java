package com.technograd.technograd.web.passwordSecurity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordSecurityUtil {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int NUMBER_OF_ITERATIONS = 30000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_MAX_SIZE = 50;


    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public static byte[] hash(char[] password, byte[] salt){
        PBEKeySpec spec = new PBEKeySpec(password, salt, NUMBER_OF_ITERATIONS,KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try{
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error during hashing password", e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String password, String salt){
        String generatedPassword;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        generatedPassword = Base64.getEncoder().encodeToString(securePassword);
        return generatedPassword;
    }

    public static boolean verifyPassword(String password, String securePassword, String salt){
        boolean isEquals;
        String newSecurePassword = generateSecurePassword(password, salt);
        isEquals = newSecurePassword.equals(securePassword);
        return isEquals;
    }
}
