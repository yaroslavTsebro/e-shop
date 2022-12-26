package com.technograd.technograd.dao.entity;

import java.sql.SQLException;

public class DBException extends SQLException {

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
