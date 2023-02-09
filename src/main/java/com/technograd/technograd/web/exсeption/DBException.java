package com.technograd.technograd.web.exсeption;


import java.sql.SQLException;

public class DBException extends SQLException {


    private static final long serialVersionUID = -8476845226697666888L;

    public DBException() {
    }

    public DBException(String reason) {
        super(reason);
    }
    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
