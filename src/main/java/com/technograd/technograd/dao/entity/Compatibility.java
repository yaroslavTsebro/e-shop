package com.technograd.technograd.dao.entity;


import java.io.Serializable;
import java.util.Objects;

public class Compatibility implements Serializable {

    private static final long serialVersionUID = -3134241583034730185L;
    private int id;
    private Category category;
    private Characteristic characteristic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public Compatibility(int id, Category category, Characteristic characteristic) {
        this.id = id;
        this.category = category;
        this.characteristic = characteristic;
    }

    public Compatibility() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compatibility that = (Compatibility) o;
        return id == that.id && Objects.equals(category, that.category) && Objects.equals(characteristic, that.characteristic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, characteristic);
    }

    @Override
    public String toString() {
        return "Compatibility{" +
                "id=" + id +
                ", category=" + category +
                ", characteristic=" + characteristic +
                '}';
    }
}
