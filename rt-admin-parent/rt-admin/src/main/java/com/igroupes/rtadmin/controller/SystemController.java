package com.igroupes.rtadmin.controller;

import com.igroupes.rtadmin.aop.LoginUser;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.*;
import com.igroupes.rtadmin.service.IPermissionService;
import com.igroupes.rtadmin.service.ISystemLogService;
import com.igroupes.rtadmin.service.IUserService;
import com.igroupes.rtadmin.util.PageDTOUtil;
import com.igroupes.rtadmin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("system")
@LoginUser
public class SystemController {
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISystemLogService systemLogService;

    @GetMapping("log/item")
    public ResultVO getLimitList(UserInfo userInfo, @Valid SystemLogListRequest request) {
        PageDTOUtil.fixPageDTO(request);
        return systemLogService.get(userInfo, request);
    }

    @GetMapping("role/filter")
    public ResultVO getFilterRole(UserInfo userInfo, FilterRoleRequest request) {
        return permissionService.getFilterRole(userInfo, request);
    }


    /**
     * 添加用户
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/user/item")
    public ResultVO addUser(UserInfo userInfo, @Valid @RequestBody UserAddRequest request) {
        return userService.addUser(userInfo, request);
    }

    @GetMapping("/user/tree")
    public ResultVO userTree(UserInfo userInfo) {
        return userService.userTree(userInfo);
    }


    @PutMapping("/user/item/{id}")
    public ResultVO updateUser(UserInfo userInfo, @PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userInfo, id, request);
    }

    @DeleteMapping("/user/item/{id}")
    public ResultVO deleteUser(UserInfo userInfo, @PathVariable Long id) {
        return userService.deleteUser(userInfo, id);
    }

    @GetMapping("/user/item/{id}")
    public ResultVO getUser(UserInfo userInfo, @PathVariable Long id) {
        return userService.getUser(userInfo, id);
    }

    @GetMapping("/user/item")
    public ResultVO getUserList(UserInfo userInfo, UserInfoPageRequest request) {
        PageDTOUtil.fixPageDTO(request);
        return userService.getUserList(userInfo, request);
    }


    @PostMapping("role/item")
    public ResultVO addRole(UserInfo userInfo, @Valid @RequestBody RoleAddRequest request) {
        return permissionService.addRole(userInfo, request);
    }

    @GetMapping("role/item")
    public ResultVO getRoleList(UserInfo userInfo, RoleListRequest request) {
        PageDTOUtil.fixPageDTO(request);
        return permissionService.getRoleList(userInfo, request);
    }


    @PutMapping("role/item/{id}/limit")
    public ResultVO updateLimit(UserInfo userInfo, @PathVariable Long id, @Valid @RequestBody PermissionUpdateRequest request) {
        return permissionService.updateLimit(userInfo, id, request);
    }

    @GetMapping("role/item/{id}/limit")
    public ResultVO getLimitList(UserInfo userInfo, @PathVariable Long id, PermissionListRequest request) {
        PageDTOUtil.fixPageDTO(request);
        return permissionService.getLimitList(userInfo, id, request);
    }

    @DeleteMapping("role/item/{id}")
    public ResultVO deleteRole(UserInfo userInfo, @PathVariable Long id) {
        return permissionService.deleteRole(userInfo, id);
    }


    @PutMapping("role/item/{id}")
    public ResultVO updateRole(UserInfo userInfo, @PathVariable Long id, @Valid @RequestBody RoleUpdateRequest request) {
        return permissionService.updateRole(userInfo, id, request);
    }

    /**
     * 将多个用户修改为指定的角色
     *
     * @param userInfo
     * @param request
     * @return
     */
    @PutMapping("role/member")
    public ResultVO updateRoleMember(UserInfo userInfo, @Valid @RequestBody RoleMemberUpdateRequest request) {
        return permissionService.updateRoleMember(userInfo, request);
    }

}
