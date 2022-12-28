package com.technograd.technograd.web.validator;

import com.technograd.technograd.web.error.AppException;

public interface Validator<T>{

    public boolean validator(T t) throws AppException;
}
