package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissionUpdateRequest {
    @NotBlank
    private String limit;
    @NotBlank
    private String api;
}
