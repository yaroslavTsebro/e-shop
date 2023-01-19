package com.technograd.technograd.dao.entity;


import java.io.Serializable;
import java.util.Objects;

public class Characteristic implements Serializable {

    private static final long serialVersionUID = 8240803670778787130L;
    private int id;
    private String nameUa;
    private String nameEn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Characteristic(int id, String nameUa, String nameEn) {
        this.id = id;
        this.nameUa = nameUa;
        this.nameEn = nameEn;
    }

    public Characteristic(String nameUa, String nameEn) {
        this.nameUa = nameUa;
        this.nameEn = nameEn;
    }
    public Characteristic(){}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characteristic characteristic = (Characteristic) o;
        return id == characteristic.id && nameUa.equals(characteristic.nameUa) && nameEn.equals(characteristic.nameEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameUa, nameEn);
    }

    @Override
    public String toString() {
        return "Characteristic{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", nameEn='" + nameEn + '\'' +
                '}';
    }
}
