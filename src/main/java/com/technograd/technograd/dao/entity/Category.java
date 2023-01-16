package com.technograd.technograd.dao.entity;


import java.io.Serializable;
import java.util.Objects;

public class Category implements Serializable {

    private static final long serialVersionUID = -9199288979567523619L;
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

    public Category(String nameUa, String nameEn) {
        this.nameUa = nameUa;
        this.nameEn = nameEn;
    }

    public Category(int id, String nameUa, String nameEn) {
        this.id = id;
        this.nameUa = nameUa;
        this.nameEn = nameEn;
    }
    public Category(){}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && nameUa.equals(category.nameUa) && nameEn.equals(category.nameEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameUa, nameEn);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", nameEn='" + nameEn + '\'' +
                '}';
    }
}