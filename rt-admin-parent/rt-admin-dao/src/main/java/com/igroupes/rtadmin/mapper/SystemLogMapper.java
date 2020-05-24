package com.igroupes.rtadmin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.igroupes.rtadmin.entity.SystemLogEntity;
import com.igroupes.rtadmin.query.SystemLogQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemLogMapper extends BaseMapper<SystemLogEntity> {

    List<SystemLogEntity> getSystemLogList(Page page , @Param("query") SystemLogQuery query);
}
