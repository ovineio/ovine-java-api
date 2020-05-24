package com.igroupes.rtadmin.service.raw;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import com.igroupes.rtadmin.entity.SystemRoleEntity;
import com.igroupes.rtadmin.mapper.SystemRoleMapper;
import com.igroupes.rtadmin.util.Requires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemRoleService extends ServiceImpl<SystemRoleMapper, SystemRoleEntity> {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    public List<SystemPermissionEntity> getChildPermList(String curParentChain) {
        Requires.requireNonBlank(curParentChain, "parent chain");
        return systemRoleMapper.getChildPermList(curParentChain);
    }

    public List<SystemRoleEntity> getFilterRole(Long userId, String filter) {
        return systemRoleMapper.getFilterRole(userId , filter);
    }


    public List<SystemRoleEntity> getChildRoleList(String curParentChain){
        Requires.requireNonBlank(curParentChain, "parent chain");
        return systemRoleMapper.getChildRoleList(curParentChain);
    }
}
