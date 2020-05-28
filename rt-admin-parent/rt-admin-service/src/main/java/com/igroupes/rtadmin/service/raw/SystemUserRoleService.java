package com.igroupes.rtadmin.service.raw;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.entity.SystemRoleEntity;
import com.igroupes.rtadmin.entity.SystemUserRoleEntity;
import com.igroupes.rtadmin.mapper.SystemUserRoleMapper;
import com.igroupes.rtadmin.result.SystemUserResult;
import com.igroupes.rtadmin.util.Requires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserRoleService extends ServiceImpl<SystemUserRoleMapper, SystemUserRoleEntity> {
    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    public SystemRoleEntity getRoleById(Long userId) {
        Requires.requireNonNull(userId, "user id");
        return systemUserRoleMapper.getRoleById(userId);
    }


    public List<SystemUserResult> getFilterUserList(Page page , Long userId, String filter, List<String> roles) {
        Requires.requireNonNull(userId, "user id");
        return systemUserRoleMapper.getFilterUserPage(page,userId, filter, roles);
    }

    public void deleteUserList(List<Long> userIds, Long roleId) {
        Requires.requireNonNull(userIds, "user ids");
        Requires.requireNonNull(roleId, "role id");
        systemUserRoleMapper.deleteUserList(userIds,roleId);
    }
}
