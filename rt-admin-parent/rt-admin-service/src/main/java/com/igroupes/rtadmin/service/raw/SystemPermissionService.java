package com.igroupes.rtadmin.service.raw;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import com.igroupes.rtadmin.mapper.SystemPermissionMapper;
import com.igroupes.rtadmin.util.Requires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemPermissionService extends ServiceImpl<SystemPermissionMapper, SystemPermissionEntity> {
    @Autowired
    private SystemPermissionMapper systemPermissionMapper;

    public SystemPermissionEntity getPermissionById(Long userId) {
        Requires.requireNonNull(userId, "user id");
        return systemPermissionMapper.getPermissionById(userId);
    }
}
