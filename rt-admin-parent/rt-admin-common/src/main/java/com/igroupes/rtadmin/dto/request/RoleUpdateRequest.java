package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleUpdateRequest {
    @NotNull
    private String name;
    private String desc ;
}
