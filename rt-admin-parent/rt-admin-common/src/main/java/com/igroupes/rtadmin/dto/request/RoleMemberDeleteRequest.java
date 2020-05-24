package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleMemberDeleteRequest {
    private List<Long> ids;
}
