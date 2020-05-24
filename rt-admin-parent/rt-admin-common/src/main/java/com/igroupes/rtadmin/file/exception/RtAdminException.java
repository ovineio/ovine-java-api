package com.igroupes.rtadmin.file.exception;

import com.igroupes.rtadmin.enums.ErrorCode;

public class RtAdminException extends BusinessException {
    private Integer code;

    public RtAdminException(String message) {
        super(message);
    }

    public RtAdminException(ErrorCode errorCode) {
        super(errorCode);
    }
}
