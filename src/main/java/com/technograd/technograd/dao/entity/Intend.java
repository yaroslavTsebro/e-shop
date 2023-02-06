package com.technograd.technograd.dao.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Intend implements Serializable {

    private static final long serialVersionUID = -7782482401155085612L;
    private int id;
    private Timestamp startDate;
    private Timestamp endDate;
    private int userId;
    private int supplierId;
    private int employeeId;
    private SendingOrReceiving sendingOrReceiving;
    private String address;
    private Condition condition;
    private List<ListIntend> listIntends;

    public List<ListIntend> getListIntends() {
        return listIntends;
    }

    public void setListIntends(List<ListIntend> listIntends) {
        this.listIntends = listIntends;
    }



    public Intend() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SendingOrReceiving getSendingOrReceiving() {
        return sendingOrReceiving;
    }

    public void setSendingOrReceiving(SendingOrReceiving sendingOrReceiving) {
        this.sendingOrReceiving = sendingOrReceiving;
    }

    public void setSendingOrReceiving(String sendingOrReceiving) {
        if(SendingOrReceiving.SENDING.toString().equalsIgnoreCase(sendingOrReceiving)){
            this.sendingOrReceiving = SendingOrReceiving.SENDING;
        } else if(SendingOrReceiving.RECEIVING.toString().equalsIgnoreCase(sendingOrReceiving)){
            this.sendingOrReceiving = SendingOrReceiving.RECEIVING;
        }
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    public void setCondition(String condition) {

        if(Condition.NEW.toString().equalsIgnoreCase(condition)){
            this.condition = Condition.NEW;
        } else if(Condition.ACCEPTED.toString().equalsIgnoreCase(condition)){
            this.condition = Condition.ACCEPTED;
        }else if(Condition.DENIED.toString().equalsIgnoreCase(condition)){
            this.condition = Condition.DENIED;
        }else if(Condition.IN_WAY.toString().equalsIgnoreCase(condition)){
            this.condition = Condition.IN_WAY;
        }else if(Condition.TURNED_BACK.toString().equalsIgnoreCase(condition)){
            this.condition = Condition.TURNED_BACK;
        }else if(Condition.CART.toString().equalsIgnoreCase(condition)){
            this.condition = Condition.CART;
        }else if(Condition.COMPLETED.toString().equalsIgnoreCase(condition)){
            this.condition = Condition.COMPLETED;
        }
    }

    @Override
    public String toString() {
        return "Intend{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", userId=" + userId +
                ", supplierId=" + supplierId +
                ", employeeId=" + employeeId +
                ", sendingOrReceiving=" + sendingOrReceiving +
                ", address='" + address + '\'' +
                ", condition=" + condition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Intend intend = (Intend) o;

        if (id != intend.id) return false;
        if (userId != intend.userId) return false;
        if (supplierId != intend.supplierId) return false;
        if (employeeId != intend.employeeId) return false;
        if (!startDate.equals(intend.startDate)) return false;
        if (!Objects.equals(endDate, intend.endDate)) return false;
        if (sendingOrReceiving != intend.sendingOrReceiving) return false;
        if (!address.equals(intend.address)) return false;
        return condition == intend.condition;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + startDate.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + supplierId;
        result = 31 * result + employeeId;
        result = 31 * result + sendingOrReceiving.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + condition.hashCode();
        return result;
    }


}
