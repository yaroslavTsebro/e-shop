package com.technograd.technograd.dao.entity;

import java.io.Serializable;

public enum Post implements Serializable {
    MANAGER,
    CUSTOMER;


    @Override
    public String toString() {
        if(this.equals(Post.MANAGER)){
            return "MANAGER";
        } else {
            return "CUSTOMER";
        }
    }
}
