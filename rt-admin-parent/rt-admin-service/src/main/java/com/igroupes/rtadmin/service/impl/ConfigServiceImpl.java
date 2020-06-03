package com.igroupes.rtadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.ListRequest;
import com.igroupes.rtadmin.dto.request.ConfigRequest;
import com.igroupes.rtadmin.dto.response.ConfigListResponse;
import com.igroupes.rtadmin.entity.ConfigEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.IConfigService;
import com.igroupes.rtadmin.service.raw.ConfigService;
import com.igroupes.rtadmin.util.PageDTOUtil;
import com.igroupes.rtadmin.util.Requires;
import com.igroupes.rtadmin.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConfigServiceImpl implements IConfigService {

    @Autowired
    private  ConfigService configService;

    @Transactional
    @Override
    public ResultVO addConfig(UserInfo userInfo, ConfigRequest request) {

        ConfigEntity configEntity = new ConfigEntity();
        BeanUtils.copyProperties(request,configEntity);
        configEntity.setAddUser(userInfo.getId());
        configService.save(configEntity);
        return ResultVO.success();
    }

    @Transactional
    @Override
    public ResultVO updateConfig(UserInfo userInfo, Long id, ConfigRequest request) {
        ConfigEntity config = checkUserRole(userInfo.getId(),id);
        config.setUpdateUser(userInfo.getId());
//        BeanUtils.copyProperties(request,config);
        config.setContent(request.getContent());
        config.setDesc(request.getDesc());
        configService.updateById(config);
        return ResultVO.success();
    }

    @Transactional
    @Override
    public ResultVO deleteConfig(UserInfo userInfo, Long id) {
        checkUserRole(userInfo.getId(),id);
        configService.removeById(id);
        return ResultVO.success();
    }

    @Override
    public ResultVO getConfigList(UserInfo userInfo, ListRequest request) {
        Page<ConfigEntity> page = new Page<>(request.getPage(), request.getSize());
        return ResultVO.success(getConfigListByPage(configService.findConfigList(page, userInfo, request)));
    }

    /**
     * 封装返回值
     * @param configDOPage
     * @return
     */
    private ConfigListResponse getConfigListByPage(IPage<ConfigEntity> configDOPage) {
        ConfigListResponse configListResponse = new ConfigListResponse();
        PageDTOUtil.setPageResponse(configDOPage, configListResponse);
        List<ConfigListResponse.ConfigListResponseDetail> list = configDOPage.getRecords().stream().map(record -> {
            ConfigListResponse.ConfigListResponseDetail detail = new ConfigListResponse.ConfigListResponseDetail();
            detail.setCreateTime(record.getAddTime());
            detail.setId(record.getId());
            detail.setContent(record.getContent());
            detail.setDesc(record.getDesc());
            detail.setUpdateTime(record.getUpdateTime());
            return detail;
        }).collect(Collectors.toList());
        configListResponse.setList(list);
        return configListResponse;
    }

    /**
     * @param userId 用户id
     * @param id 配置id
     */
    private ConfigEntity checkUserRole(Long userId,Long id) {
        Requires.requireNonNull(userId, "user id");
        Requires.requireNonNull(id, "config id");
        ConfigEntity configEntity = configService.getById(id);
        if (null == configEntity) {
            throw new RtAdminException(ErrorCode.PARAM_ERROR);
        }
        if(!configEntity.getAddUser().equals(userId)){
            throw new RtAdminException(ErrorCode.PERMISSION_DENIED);
        }
        return configEntity;
    }

}
