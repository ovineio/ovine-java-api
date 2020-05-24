package com.igroupes.rtadmin.enums;

public enum ErrorCode implements ResultEnum {

    SYSTEM_ERROR(-1,"系统异常"),
    PARAM_ERROR(10007,"参数错误"),
    USER_NOT_EXISTS(10020,"用户不存在"),
    PASSWORD_ERROR(10021,"密码错误"),
    PASSWORD_FORMAT_ERROR(10019,"密码格式错误"),
    TOKEN_NOT_EXISTS(10022,"token不存在"),
    TOKEN_EXPIRE(10023,"token过期"),
    PASSWORD_REPEAT(10024,"新旧密码一致"),
    PERMISSION_DENIED(10034,"权限不足"),
    CASCADING_DELETION_EXCEPTION(10054,"不能级联删除"),
    LIMIT_FORMAT_ERROR(10064,"接口权限格式错误"),
    VERIFY_CODE_ERROR(10074,"验证码错误"),
    USER_EXISTS(10084,"用户已经存在"),
    FILE_UPLOAD_FAIL(10094,"文件上传失败"),
    FILE_DOWNLOAD_FAIL(10095,"文件下载失败"),
    CONF_FORMAT_EXCEPTION(10200,"配置格式异常"),
    FILE_TYPE_ERROR(10100,"文件类型错误"),
    ROLE_BIND_USER(10110,"角色已经绑定了用户"),
    ROLE_CHILD_EXIST(10112,"存在子角色"),
    STAT_CODE_NOT_EXIST(10115,"统计code不存在"),
    ;

    ;
    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
