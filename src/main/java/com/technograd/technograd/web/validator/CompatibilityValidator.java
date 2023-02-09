package com.technograd.technograd.web.validator;

import com.technograd.technograd.dao.entity.Compatibility;
import com.technograd.technograd.web.exсeption.AppException;

public class CompatibilityValidator implements Validator<Compatibility> {
    @Override
    public boolean validator(Compatibility compatibility) throws AppException {
        try {
            return validateCompatibilityForeignId(compatibility.getCategory().getId(), "compatibility.validator.category.id.is.negative") &&
                    validateCompatibilityForeignId(compatibility.getCharacteristic().getId(), "compatibility.validator.characteristic.id.is.negative");
        } catch (AppException e){
            throw new AppException(e);
        }
    }

    private static boolean validateCompatibilityForeignId(int id, String ex) throws AppException {
        if(id <= 0) {
            throw new AppException(ex);
        }
        return true;
    }

}
