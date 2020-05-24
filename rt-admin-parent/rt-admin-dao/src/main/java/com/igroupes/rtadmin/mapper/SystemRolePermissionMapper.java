package com.igroupes.rtadmin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import com.igroupes.rtadmin.entity.SystemRolePermissionEntity;
import org.apache.ibatis.annotations.Param;

public interface SystemRolePermissionMapper extends BaseMapper<SystemRolePermissionEntity> {

    SystemPermissionEntity getPermissionById(@Param("roleId") Long roleId);

}
