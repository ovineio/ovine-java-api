package com.igroupes.rtadmin.util;

import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.BusinessException;

import javax.validation.ConstraintViolationException;

public class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static BusinessException warp(Exception exception) {
        if (exception instanceof BusinessException) {
            return (BusinessException) exception;
        } else if(exception instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException)exception;
            ErrorCode ec = ErrorCode.PARAM_ERROR;
            ec.setMessage(ex.getMessage());
            return new BusinessException(ec);
        }else{
            return new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
