package com.igroupes.rtadmin.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRegisterRequest {
    @Length(min = 6, message = "用户名长度至少6位")
    private String username;
    @Length(min = 6, message = "密码长度至少6位")
    private String password;
}
