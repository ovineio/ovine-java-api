package com.igroupes.rtadmin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.igroupes.rtadmin.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("addTime", DateUtils.getDate(DateUtils.DATETIME_FORMAT),metaObject);
        this.setFieldValByName("updateTime", DateUtils.getDate(DateUtils.DATETIME_FORMAT),metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", DateUtils.getDate(DateUtils.DATETIME_FORMAT),metaObject);
    }
}