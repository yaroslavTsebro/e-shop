package com.technograd.technograd.web.validator;

import com.technograd.technograd.web.exeption.AppException;

public interface Validator<T>{

    public boolean validator(T t) throws AppException;
}
