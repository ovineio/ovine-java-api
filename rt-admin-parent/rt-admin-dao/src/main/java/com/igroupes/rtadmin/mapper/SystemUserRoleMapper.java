package com.igroupes.rtadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.igroupes.rtadmin.entity.SystemRoleEntity;
import com.igroupes.rtadmin.entity.SystemUserRoleEntity;
import com.igroupes.rtadmin.result.SystemUserResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserRoleMapper extends BaseMapper<SystemUserRoleEntity> {

    SystemRoleEntity getRoleById(@Param("userId") Long userId);

    List<SystemUserResult> getFilterUserPage(Page page , @Param("userId") Long userId, @Param("filter") String filter, @Param("roleIds")  List<String> roles);

    void deleteUserList(@Param("userIds") List<Long> userIds , @Param("roleId") Long roleId);
}

