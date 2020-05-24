package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleMemberAddRequest {
    private List<Long> ids;
}
