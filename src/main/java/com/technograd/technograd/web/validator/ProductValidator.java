package com.technograd.technograd.web.validator;

import com.technograd.technograd.dao.entity.Product;

import java.math.BigDecimal;

public class ProductValidator implements Validator<Product>{
    @Override
    public boolean validator(Product product) {
        if(product.getPrice().signum() == 0 ||product.getPrice().signum() == -1){
            return false;
        }
        if(product.getNameEn().length() > 50 || product.getNameEn().isEmpty()){
            return false;
        }
        if(product.getNameUa().length() > 50 || product.getNameUa().isEmpty()){
            return false;
        }
        if(product.getWeight() <= 0){
            return false;
        }
        if(product.getCategory().getId() <= 0 || product.getCompany().getId() <=0){
            return false;
        }
        if(product.getCount() <= 0 || product.getWarranty() < 0){
            return false;
        }
        if(product.getTitleUa().length() > 50 || product.getTitleEn().length() > 5){
            return false;
        }
        if(product.getDescriptionEn().length() > 500 || product.getDescriptionUa().length() > 5){
            return false;
        }
        return true;
    }
}
