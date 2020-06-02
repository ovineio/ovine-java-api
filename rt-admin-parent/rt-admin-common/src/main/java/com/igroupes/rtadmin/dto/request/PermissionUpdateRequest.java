package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissionUpdateRequest {
    @NotBlank(message = "limit is blank")
    private String limit;
    @NotBlank(message = "api is blank")
    private String api;
}
