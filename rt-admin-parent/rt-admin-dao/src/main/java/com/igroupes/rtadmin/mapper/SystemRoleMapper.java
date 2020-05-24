package com.igroupes.rtadmin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import com.igroupes.rtadmin.entity.SystemRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemRoleMapper extends BaseMapper<SystemRoleEntity> {
    /**
     * 注意：如果需要查找某一个role的子角色，需要先拼出该角色的完整role-chain
     * @param curParentChain
     * @return
     */
    List<SystemPermissionEntity> getChildPermList(@Param("curParentChain") String curParentChain);

    List<SystemRoleEntity> getFilterRole(@Param("userId")Long userId, @Param("filter") String filter);

    List<SystemRoleEntity> getChildRoleList(@Param("curParentChain") String curParentChain);
}

