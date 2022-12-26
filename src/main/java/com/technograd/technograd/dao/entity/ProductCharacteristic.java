package com.technograd.technograd.dao.entity;

import java.util.Objects;

public class ProductCharacteristic {
    private int id;
    private int productId;
    private Compatibility compatibility;
    private String value;

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

    public Compatibility getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(Compatibility compatibility) {
        this.compatibility = compatibility;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductCharacteristic(int id, int productId, Compatibility compatibility, String value) {
        this.id = id;
        this.productId = productId;
        this.compatibility = compatibility;
        this.value = value;
    }

    public ProductCharacteristic() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCharacteristic that = (ProductCharacteristic) o;
        return id == that.id && productId == that.productId && compatibility.equals(that.compatibility) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, compatibility, value);
    }

    @Override
    public String toString() {
        return "ProductCharacteristic{" +
                "id=" + id +
                ", productId=" + productId +
                ", compatibility=" + compatibility +
                ", value='" + value + '\'' +
                '}';
    }
}
