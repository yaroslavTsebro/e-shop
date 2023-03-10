package com.technograd.technograd.dao.entity;


import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {

    private static final long serialVersionUID = -5296116681000710925L;
    private int id;
    private String nameUa;
    private String nameEn;
    private Country country;


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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


    public Company() {
    }

    public Company(int id, String nameUa, String nameEn, Country country) {
        this.id = id;
        this.nameUa = nameUa;
        this.nameEn = nameEn;
        this.country = country;
    }

    public Company(String nameUa, String nameEn, Country country) {
        this.nameUa = nameUa;
        this.nameEn = nameEn;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", country=" + country +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        if (!nameUa.equals(company.nameUa)) return false;
        if (!nameEn.equals(company.nameEn)) return false;
        return country.equals(company.country);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nameUa.hashCode();
        result = 31 * result + nameEn.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }
}
