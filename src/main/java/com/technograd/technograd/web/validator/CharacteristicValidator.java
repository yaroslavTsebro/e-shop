package com.technograd.technograd.web.validator;

import com.technograd.technograd.dao.entity.Characteristic;
import com.technograd.technograd.web.error.AppException;

public class CharacteristicValidator implements Validator<Characteristic> {
private static final String characteristicUaPattern = "^[а-яА-ЯІіЇї\\s]+";
private static final String characteristicEnPattern = "^[a-zA-Z\\s]+";
private static final int nameSize = 50;

@Override
public boolean validator(Characteristic characteristic) throws AppException {
        try {
                return validateCharacteristicNameUa(characteristic.getNameUa()) &&
                        validateCharacteristicNameEn(characteristic.getNameEn());
        } catch (AppException e) {
            throw new AppException(e);
        }
}


private static boolean validateCharacteristicNameUa(String nameUa) throws AppException {
        if(nameUa.isEmpty()){
        throw new AppException("characteristic.validator.name.ua.length.is.empty");
        }
        if(nameUa.length() > nameSize){
        throw new AppException("characteristic.validator.name.ua.length.doesnt.matches");
        }
        if(!nameUa.matches(characteristicUaPattern)){
        throw new AppException("characteristic.validator.name.ua.doesnt.matches");
        }
        return true;
        }

private static boolean validateCharacteristicNameEn(String nameEn) throws AppException {
        if(nameEn.isEmpty()){
        throw new AppException("characteristic.validator.name.en.length.is.empty");
        }
        if(nameEn.length() > nameSize){
        throw new AppException("characteristic.validator.name.en.length.doesnt.matches");
        }
        if(!nameEn.matches(characteristicEnPattern)){
        throw new AppException("characteristic.validator.name.en.doesnt.matches");
        }
        return true;
        }
}
