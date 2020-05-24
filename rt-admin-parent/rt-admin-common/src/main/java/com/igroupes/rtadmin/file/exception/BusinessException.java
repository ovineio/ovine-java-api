package com.igroupes.rtadmin.file.exception;

import com.igroupes.rtadmin.enums.ResultEnum;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private Integer statusCode;
    private Integer code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }


    public BusinessException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BusinessException(Integer statusCode, String message, Throwable t) {
        super(message,t);
        this.statusCode = statusCode;
    }

    public BusinessException(String message, Throwable t) {
        super(message,t);
    }
}
