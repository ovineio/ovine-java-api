package com.igroupes.rtadmin.service;

import lombok.Data;

public interface IVerifyCodeManager {

    public VerifyCode genCode(CodeType codeType);
    public boolean isRightVerifyCode(Long codeKey, String code);
    public String getVerifyCode(Long codeKey);
    public void deleteVerifyCode(Long codeKey);
    public void close(); // 关闭资源
    /**
     * T代表code对应的资源，可能是图片
     *
     * @param <T>
     */
    @Data
    public class VerifyCode<T> {
        private String code;
        private T data;
        private Long codeId; // 当前校验码的唯一标识
    }

    public enum CodeType {
        SIMPLE_PICTURE,
    }
}
