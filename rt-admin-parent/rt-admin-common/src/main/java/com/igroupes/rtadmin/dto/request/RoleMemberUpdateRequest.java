package com.igroupes.rtadmin.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleMemberUpdateRequest {
    @NotNull
    private List<Long> userIds;
    @NotNull
    private Long roleId;
}
