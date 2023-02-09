package com.technograd.technograd.web.exсeption;

public class AppException extends Exception{

    private static final long serialVersionUID = 2656012917531641053L;

    public AppException() {
    }

    public AppException(Throwable cause) {
        super(cause);
    }
    public AppException(String message) {
        super(message);
    }
    
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
