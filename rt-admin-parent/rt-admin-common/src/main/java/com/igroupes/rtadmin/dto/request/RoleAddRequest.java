package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleAddRequest {
    @NotBlank
    private String name;
    private String desc ;
}
