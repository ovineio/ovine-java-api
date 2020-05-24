package com.igroupes.rtadmin.service.raw;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.entity.SystemUserEntity;
import com.igroupes.rtadmin.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserService extends ServiceImpl<SystemUserMapper, SystemUserEntity> {
    @Autowired
    private SystemUserMapper systemUserMapper;

    public List<SystemUserEntity> getUserChain(String parentChain) {
        return systemUserMapper.getUserChain(parentChain);
    }

}
