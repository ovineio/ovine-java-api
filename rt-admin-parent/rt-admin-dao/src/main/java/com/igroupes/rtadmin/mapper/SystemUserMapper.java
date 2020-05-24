package com.igroupes.rtadmin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.igroupes.rtadmin.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserMapper extends BaseMapper<SystemUserEntity> {

    /**
     * 获取用户链
     *
     * @return
     */
    List<SystemUserEntity> getUserChain(@Param("parentChain") String parentChain);
}
