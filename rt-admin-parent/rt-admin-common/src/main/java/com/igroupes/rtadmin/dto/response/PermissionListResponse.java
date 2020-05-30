package com.igroupes.rtadmin.dto.response;

import com.igroupes.rtadmin.dto.PageResponse;
import lombok.Data;

@Data
public class PermissionListResponse extends PageResponse{
    private String limit;
}
