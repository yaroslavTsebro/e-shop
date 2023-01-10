package com.technograd.technograd.web.validator;

import com.technograd.technograd.dao.entity.Category;
import com.technograd.technograd.web.exeption.AppException;

public class CategoryValidator implements Validator<Category>{
    private static final String categoryUaPattern = "^[а-яА-ЯІіЇї\\s]+";
    private static final String categoryEnPattern = "^[a-zA-Z\\s]+";
    private static final int nameSize = 50;
    @Override
    public boolean validator(Category category) throws AppException {
        try {
            return validateCategoryNameUa(category.getNameUa()) &&
                    validateCategoryNameEn(category.getNameEn());
        } catch (AppException e) {
            throw new AppException(e);
        }

    }


    private static boolean validateCategoryNameUa(String nameUa) throws AppException {
        if(nameUa.isEmpty()){
            throw new AppException("category.validator.name.ua.length.is.empty");
        }
        if(nameUa.length() > nameSize){
            throw new AppException("category.validator.name.ua.length.doesnt.matches");
        }
        if(!nameUa.matches(categoryUaPattern)){
           throw new AppException("category.validator.name.ua.doesnt.matches");
        }
        return true;
    }

    private static boolean validateCategoryNameEn(String nameEn) throws AppException {
        if(nameEn.isEmpty()){
            throw new AppException("category.validator.name.en.length.is.empty");
        }
        if(nameEn.length() > nameSize){
            throw new AppException("category.validator.name.en.length.doesnt.matches");
        }
        if(!nameEn.matches(categoryEnPattern)){
            throw new AppException("category.validator.name.en.doesnt.matches");
        }
        return true;
    }
}
