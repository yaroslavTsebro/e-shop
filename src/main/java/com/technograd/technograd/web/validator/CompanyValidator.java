package com.technograd.technograd.web.validator;

import com.technograd.technograd.dao.entity.Company;
import com.technograd.technograd.web.exсeption.AppException;

public class CompanyValidator implements Validator<Company> {
    private static final String companyEnPattern = "^[a-zA-Z\\s]+";
    private static final int nameSize = 50;

    @Override
    public boolean validator(Company company) throws AppException {
        try {
            return validateCompanyNameUa(company.getNameUa()) &&
                    validateCompanyNameEn(company.getNameEn());
        } catch (AppException e) {
            throw new AppException(e);
        }
    }


    private static boolean validateCompanyNameUa(String nameUa) throws AppException {
        if(nameUa.isEmpty()){
            throw new AppException("company.validator.name.ua.length.is.empty");
        }
        if(nameUa.length() > nameSize){
            throw new AppException("company.validator.name.ua.length.doesnt.matches");
        }
        return true;
    }

    private static boolean validateCompanyNameEn(String nameEn) throws AppException {
        if(nameEn.isEmpty()){
            throw new AppException("company.validator.name.en.length.is.empty");
        }
        if(nameEn.length() > nameSize){
            throw new AppException("company.validator.name.en.length.doesnt.matches");
        }
        if(!nameEn.matches(companyEnPattern)){
            throw new AppException("company.validator.name.en.doesnt.matches");
        }
        return true;
    }

    private static boolean validateCompanyCountryUa(String countryUa) throws AppException {
        if(countryUa.isEmpty()){
            throw new AppException("company.validator.country.ua.length.is.empty");
        }
        if(countryUa.length() > nameSize){
            throw new AppException("company.validator.country.ua.length.doesnt.matches");
        }
        return true;
    }

    private static boolean validateCompanyCountryEn(String countryEn) throws AppException {
        if(countryEn.isEmpty()){
            throw new AppException("company.validator.country.en.length.is.empty");
        }
        if(countryEn.length() > nameSize){
            throw new AppException("company.validator.country.en.length.doesnt.matches");
        }
        if(!countryEn.matches(companyEnPattern)){
            throw new AppException("company.validator.country.en.doesnt.matches");
        }
        return true;
    }

}
