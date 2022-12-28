package com.technograd.technograd.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Product implements Serializable {
    private static final long serialVersionUID = -6529673480271382127L;

    private int id;
    private String nameUa;
    private String nameEn;
    private BigDecimal price;
    private int weight;
    private Category category;
    private Company company;
    private int count;
    private int warranty;
    private String titleUa;
    private String titleEn;
    private String descriptionUa;
    private String descriptionEn;
    private List<Photo> photos;
    private List<ProductCharacteristic> productCharacteristics;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getTitleUa() {
        return titleUa;
    }

    public void setTitleUa(String titleUa) {
        this.titleUa = titleUa;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public List<Photo> getPhoto() {
        return photos;
    }

    public void setPhoto(List<Photo> photos) {
        this.photos = photos;
    }

    public List<ProductCharacteristic> getProductCharacteristics() {
        return productCharacteristics;
    }

    public void setProductCharacteristics(List<ProductCharacteristic> productCharacteristics) {
        this.productCharacteristics = productCharacteristics;
    }

    public Product() {
    }


    @Override
    public String toString() {
        String photosString = "";
        for (Photo photo : photos) {
            photosString += photo.toString();
        }
        String productCharacteristicsString = "";
        for (ProductCharacteristic characteristic : productCharacteristics) {
            productCharacteristicsString += characteristic.toString();
        }
        return "Product{" +
                "id=" + id +
                ", nameUa='" + nameUa + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", category=" + category +
                ", company=" + company +
                ", count=" + count +
                ", warranty=" + warranty +
                ", titleUa='" + titleUa + '\'' +
                ", titleEn='" + titleEn + '\'' +
                ", descriptionUa='" + descriptionUa + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", photos=['" + photosString + '\'' +
                "], productCharacteristics=['" + productCharacteristicsString + '\'' +
                "]}";
    }

}
