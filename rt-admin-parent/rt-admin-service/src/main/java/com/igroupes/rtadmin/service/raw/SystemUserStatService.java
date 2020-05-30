package com.igroupes.rtadmin.service.raw;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.entity.SystemUserStatEntity;
import com.igroupes.rtadmin.mapper.SystemUserStatMapper;
import com.igroupes.rtadmin.result.StatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserStatService extends ServiceImpl<SystemUserStatMapper, SystemUserStatEntity> {
    @Autowired
    private SystemUserStatMapper systemUserStatMapper;


    public List<StatResult> getStat(String startDate , String endDate) {
        return systemUserStatMapper.getStat(startDate,endDate);
    }

    public Integer getUserCount(String endDate) {
        return systemUserStatMapper.getUserCount(endDate);
    }
}
