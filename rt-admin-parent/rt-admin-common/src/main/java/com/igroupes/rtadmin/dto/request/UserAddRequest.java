package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAddRequest {
    @NotBlank
    private String username;
    private String nickname;
    private String avatar;
    private String desc;
    @NotBlank
    private String password;
}
