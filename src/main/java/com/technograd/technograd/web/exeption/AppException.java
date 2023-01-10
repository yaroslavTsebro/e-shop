package com.technograd.technograd.web.exeption;

public class AppException extends Exception{

    private static final long serialVersionUID = 2656012917531641053L;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
