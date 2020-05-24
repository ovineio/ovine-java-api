package com.igroupes.rtadmin.service;

import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.*;
import com.igroupes.rtadmin.vo.ResultVO;

import javax.validation.Valid;

public interface IPermissionService {
    ResultVO addRole(UserInfo userInfo, RoleAddRequest request);

    ResultVO updateLimit(UserInfo userInfo, Long id, PermissionUpdateRequest request);

    ResultVO getLimitList(UserInfo userInfo, Long roleId, PermissionListRequest request);

    ResultVO deleteRole(UserInfo userInfo, Long roleId);

    ResultVO getRoleList(UserInfo userInfo, @Valid RoleListRequest request);

    ResultVO getFilterRole(UserInfo userInfo,FilterRoleRequest request);

    ResultVO updateRole(UserInfo userInfo, Long roleId, RoleUpdateRequest request);

    ResultVO updateRoleMember(UserInfo userInfo, @Valid RoleMemberUpdateRequest request);
}
