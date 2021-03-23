package com.igroupes.rtadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.igroupes.rtadmin.entity.SystemUserStatEntity;
import com.igroupes.rtadmin.result.StatResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserStatMapper extends BaseMapper<SystemUserStatEntity>{

    List<StatResult> getStat(@Param("startDate") String startDate ,@Param("endDate") String endDate);


    Integer getUserCount(@Param("endDate") String endDate);
}
