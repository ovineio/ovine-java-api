package com.igroupes.rtadmin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import org.apache.ibatis.annotations.Param;

public interface SystemPermissionMapper extends BaseMapper<SystemPermissionEntity> {

    SystemPermissionEntity getPermissionById(@Param("userId") Long userId);
}
