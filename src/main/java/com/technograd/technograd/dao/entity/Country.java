package com.technograd.technograd.dao.entity;

import java.io.Serializable;

public class Country implements Serializable {
    private static final long serialVersionUID = 1972508771747044043L;
    private int id;
    private String nameUa;
    private String nameEn;


    public Country() {
    }

    public Country(String nameUa, String nameEn) {
        this.nameUa = nameUa;
        this.nameEn = nameEn;
    }

    public Country(int id, String nameUa, String nameEn) {
        this.id = id;
        this.nameUa = nameUa;
        this.nameEn = nameEn;
    }

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

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", nameEn='" + nameEn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (id != country.id) return false;
        if (!nameUa.equals(country.nameUa)) return false;
        return nameEn.equals(country.nameEn);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nameUa.hashCode();
        result = 31 * result + nameEn.hashCode();
        return result;
    }

}
