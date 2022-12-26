package com.technograd.technograd.dao.entity;

import java.sql.Date;
import java.util.Objects;

public class IntendReturn {
    private int id;
    private int intendId;
    private Date date;
    private String reason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntendId() {
        return intendId;
    }

    public void setIntendId(int intendId) {
        this.intendId = intendId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public IntendReturn() {
    }

    public IntendReturn(int id, int intendId, Date date, String reason) {
        this.id = id;
        this.intendId = intendId;
        this.date = date;
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntendReturn that = (IntendReturn) o;
        return id == that.id && intendId == that.intendId && date.equals(that.date) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, intendId, date, reason);
    }

    @Override
    public String toString() {
        return "IntendReturn{" +
                "id=" + id +
                ", intendId=" + intendId +
                ", date=" + date +
                ", reason='" + reason + '\'' +
                '}';
    }
}
