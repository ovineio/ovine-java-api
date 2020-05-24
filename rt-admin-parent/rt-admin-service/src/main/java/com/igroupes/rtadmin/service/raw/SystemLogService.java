package com.igroupes.rtadmin.service.raw;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.entity.SystemLogEntity;
import com.igroupes.rtadmin.mapper.SystemLogMapper;
import com.igroupes.rtadmin.query.SystemLogQuery;
import com.igroupes.rtadmin.util.Requires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogService extends ServiceImpl<SystemLogMapper, SystemLogEntity> {
    @Autowired
    private SystemLogMapper systemLogMapper;

    public List<SystemLogEntity> getSystemLogList(Page page, SystemLogQuery query) {
        Requires.requireNonNull(query.getUserId() , "user id");
        return systemLogMapper.getSystemLogList(page, query);
    }

}