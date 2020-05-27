package com.igroupes.rtadmin.util;

import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Slf4j
public class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static BusinessException warp(Exception exception) {
        try {

            if (exception instanceof BusinessException) {
                return (BusinessException) exception;
            } else if (exception instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) exception;
                Set<ConstraintViolation<?>> cves = cve.getConstraintViolations();
                StringBuffer errorMsg = new StringBuffer();
                cves.forEach(ex -> errorMsg.append(ex.getMessage()).append(";"));
                ErrorCode ec = ErrorCode.PARAM_ERROR;
                ec.setMessage(errorMsg.toString());
                return new BusinessException(ec);
            } else if (exception instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException c = (MethodArgumentNotValidException) exception;
                List<ObjectError> errors = c.getBindingResult().getAllErrors();
                StringBuffer errorMsg = new StringBuffer();
                errors.stream().forEach(x -> {
                    errorMsg.append(x.getDefaultMessage()).append(";");
                });
                ErrorCode ec = ErrorCode.PARAM_ERROR;
                ec.setMessage(errorMsg.toString());
                return new BusinessException(ec);
            } else {
                return new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }catch (Exception ex){
            log.error("exception:" ,ex);
            return new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

}
