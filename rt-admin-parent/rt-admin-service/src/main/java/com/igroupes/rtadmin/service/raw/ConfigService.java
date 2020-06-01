package com.igroupes.rtadmin.service.raw;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.ListRequest;
import com.igroupes.rtadmin.entity.ConfigEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.mapper.ConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;

@Service
public class ConfigService extends ServiceImpl<ConfigMapper, ConfigEntity> {

    @Autowired
    private ConfigMapper configMapper;

    public IPage<ConfigEntity> findConfigList(Page<ConfigEntity> page, UserInfo userInfo, ListRequest request){
        QueryWrapper<ConfigEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ConfigEntity::getAddUser, userInfo.getId());
        try {
            if (StringUtils.isNotBlank(request.getKey())) {
                queryWrapper.lambda().like(ConfigEntity::getContent, URLDecoder.decode(request.getKey(), RtAdminConstant.URL_DECODE_CHARSET));
            }
            if (StringUtils.isNotBlank(request.getEndDate())) {
                queryWrapper.lambda().lt(ConfigEntity::getAddTime,URLDecoder.decode(request.getEndDate(), RtAdminConstant.URL_DECODE_CHARSET));
            }
            if (StringUtils.isNotBlank(request.getStartDate())) {
                queryWrapper.lambda().ge(ConfigEntity::getAddTime,URLDecoder.decode(request.getStartDate(), RtAdminConstant.URL_DECODE_CHARSET));
            }
        } catch (Exception ex) {
            throw new RtAdminException(ErrorCode.PARAM_ERROR);
        }
        return  configMapper.selectPage(page, queryWrapper);
    }
}
