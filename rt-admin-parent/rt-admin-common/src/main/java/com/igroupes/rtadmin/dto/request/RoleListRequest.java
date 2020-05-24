package com.igroupes.rtadmin.dto.request;

import com.igroupes.rtadmin.dto.PageRequest;
import lombok.Data;

@Data
public class RoleListRequest extends PageRequest{
    private String roleIds;
}
