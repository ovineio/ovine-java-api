package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String code;
}
