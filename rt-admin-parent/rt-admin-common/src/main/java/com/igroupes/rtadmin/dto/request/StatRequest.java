package com.igroupes.rtadmin.dto.request;

import com.igroupes.rtadmin.enums.CodeMessageEnum;
import lombok.Data;

@Data
public class StatRequest {
    private Integer code;
    private String detail;

    public enum StatCode implements CodeMessageEnum<Integer> {
        REFRESH_PAGE(100001, "刷新页面");

        StatCode(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        private String message;
        private Integer code;

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public Integer getCode() {
            return code;
        }
    }
}
