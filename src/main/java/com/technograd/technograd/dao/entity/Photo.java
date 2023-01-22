package com.technograd.technograd.dao.entity;


import java.io.Serializable;
import java.util.Objects;

public class Photo implements Serializable {

    private static final long serialVersionUID = 6137404746732148611L;
    private int id;
    private int productId;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Photo(int id, int productId, String name) {
        this.id = id;
        this.productId = productId;
        this.name = name;
    }

    public Photo( String name) {
        this.name = name;
    }
    public Photo( String name, int productId) {
        this.name = name;
        this.productId = productId;
    }
    public Photo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id == photo.id && productId == photo.productId && name.equals(photo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                '}';
    }
}
