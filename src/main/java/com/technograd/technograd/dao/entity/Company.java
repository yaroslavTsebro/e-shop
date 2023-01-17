package com.technograd.technograd.dao.entity;


import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {

    private static final long serialVersionUID = -5296116681000710925L;
    private int id;
    private String nameUa;
    private String nameEn;
    private String countryUa;
    private String countryEn;

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

    public String getCountryUa() {
        return countryUa;
    }

    public void setCountryUa(String countryUa) {
        this.countryUa = countryUa;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public Company() {
    }

    public Company(int id, String nameUa, String nameEn, String countryUa, String countryEn) {
        this.id = id;
        this.nameUa = nameUa;
        this.nameEn = nameEn;
        this.countryUa = countryUa;
        this.countryEn = countryEn;
    }

    public Company(String nameUa, String nameEn, String countryUa, String countryEn) {
        this.nameUa = nameUa;
        this.nameEn = nameEn;
        this.countryUa = countryUa;
        this.countryEn = countryEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && nameUa.equals(company.nameUa) && nameEn.equals(company.nameEn) && countryUa.equals(company.countryUa) && countryEn.equals(company.countryEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameUa, nameEn, countryUa, countryEn);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", nameUa=" + nameUa +
                ", nameEn=" + nameEn +
                ", countryUa=" + countryUa +
                ", countryEn=" + countryEn +
                '}';
    }

}
