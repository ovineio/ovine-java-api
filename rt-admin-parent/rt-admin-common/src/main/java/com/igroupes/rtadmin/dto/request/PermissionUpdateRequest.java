package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PermissionUpdateRequest {
    // 说明： 允许为空表示无权限
    private String limit;
    private String api;
}
