package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String oldPassword;
}
