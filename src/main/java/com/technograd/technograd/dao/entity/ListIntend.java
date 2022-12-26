package com.technograd.technograd.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class ListIntend {
    private int id;
    private int intendId;
    private int count;
    private Product product;
    private BigDecimal productPrice;


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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListIntend that = (ListIntend) o;

        if (id != that.id) return false;
        if (intendId != that.intendId) return false;
        if (count != that.count) return false;
        if (!product.equals(that.product)) return false;
        return productPrice.equals(that.productPrice);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + intendId;
        result = 31 * result + count;
        result = 31 * result + product.hashCode();
        result = 31 * result + productPrice.hashCode();
        return result;
    }
    public ListIntend() {}

    @Override
    public String toString() {
        return "ListIntend{" +
                "id=" + id +
                ", intendId=" + intendId +
                ", count=" + count +
                ", product=" + product +
                ", productPrice=" + productPrice +
                '}';
    }
}
