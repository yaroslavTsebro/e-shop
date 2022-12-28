package com.technograd.technograd.web.validator;

import com.technograd.technograd.dao.entity.Product;

public class ProductValidator implements Validator<Product>{
    @Override
    public boolean validator(Product product) {
        return false;
    }
}
