package com.igroupes.rtadmin.service.raw;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import com.igroupes.rtadmin.entity.SystemRolePermissionEntity;
import com.igroupes.rtadmin.mapper.SystemRolePermissionMapper;
import com.igroupes.rtadmin.util.Requires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemRolePermissionService extends ServiceImpl<SystemRolePermissionMapper, SystemRolePermissionEntity> {
    @Autowired
    private SystemRolePermissionMapper systemRolePermissionMapper;


    public SystemPermissionEntity getPermissionById(Long roleId) {
        Requires.requireNonNull(roleId,"role id");
        return systemRolePermissionMapper.getPermissionById(roleId);
    }

}
