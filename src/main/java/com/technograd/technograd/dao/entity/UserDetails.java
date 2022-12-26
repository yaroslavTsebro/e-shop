package com.technograd.technograd.dao.entity;

import java.util.Objects;

public class UserDetails {
    private int userId;
    private String code;
    private String salt;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserDetails(int userId, String code, String salt) {
        this.userId = userId;
        this.code = code;
        this.salt = salt;
    }

    public UserDetails() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return userId == that.userId && code.equals(that.code) && salt.equals(that.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, code, salt);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", code='" + code + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
