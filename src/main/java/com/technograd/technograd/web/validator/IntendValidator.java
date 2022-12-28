package com.technograd.technograd.web.validator;

import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.web.error.AppException;

public class IntendValidator implements Validator<Intend>{
    @Override
    public boolean validator(Intend intend) throws AppException {
        try {
            if(intend.getSendingOrReceiving().equals("SENDING")){
               return validateSending(intend);
            } else{
               return validateReceiving(intend);
            }
        } catch (AppException e) {
            throw new AppException(e);
        }
    }

    public boolean validateSending(Intend intend) throws AppException {
        return  validateCompatibilityForeignId(intend.getUserId(), "intend.validator.user.id.is.negative ") &&
                validateCompatibilityForeignId(intend.getEmployeeId(), "intend.validator.employee.id.is.negative") &&
                validateIntendAddress(intend.getAddress());
    }

    public boolean validateReceiving(Intend intend) throws AppException {
        return  validateCompatibilityForeignId(intend.getSupplierId(), "intend.validator.supplier.id.is.negative") &&
                validateCompatibilityForeignId(intend.getEmployeeId(), "intend.validator.employee.id.is.negative");
    }

    private static boolean validateCompatibilityForeignId(int id, String ex) throws AppException {
        if(id <= 0) {
            throw new AppException(ex);
        }
        return true;
    }
    private static boolean validateIntendAddress(String address) throws AppException {
        if(address.length() == 0 || address.length() > 120) {
            throw new AppException("intend.validator.address.is.invalid");
        }
        return true;
    }

}
