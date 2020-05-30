package com.igroupes.rtadmin.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.response.SystemLogListResponse;
import com.igroupes.rtadmin.dto.request.SystemLogListRequest;
import com.igroupes.rtadmin.entity.SystemLogEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.query.SystemLogQuery;
import com.igroupes.rtadmin.service.ISystemLogService;
import com.igroupes.rtadmin.service.raw.SystemLogService;
import com.igroupes.rtadmin.util.PageDTOUtil;
import com.igroupes.rtadmin.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemLogServiceImpl implements ISystemLogService {
    @Autowired
    private SystemLogService systemLogService;


    @Override
    public ResultVO get(UserInfo userInfo, @Valid SystemLogListRequest request) {
        try {
            if (StringUtils.isNotBlank(request.getActionAddr())) {
                // roleId经过框架处理，会转为url编码
                request.setActionAddr(URLDecoder.decode(request.getActionAddr(), RtAdminConstant.URL_DECODE_CHARSET));
            }
            if (StringUtils.isNotBlank(request.getEndTime())) {
                request.setEndTime(URLDecoder.decode(request.getEndTime(), RtAdminConstant.URL_DECODE_CHARSET));
            }
            if (StringUtils.isNotBlank(request.getStartTime())) {
                request.setStartTime(URLDecoder.decode(request.getStartTime(), RtAdminConstant.URL_DECODE_CHARSET));
            }
            if (StringUtils.isNotBlank(request.getHandlerFilter())) {
                request.setHandlerFilter(URLDecoder.decode(request.getHandlerFilter(), RtAdminConstant.URL_DECODE_CHARSET));
            }
        } catch (Exception ex) {
            throw new RtAdminException(ErrorCode.PARAM_ERROR);
        }
        Page<SystemLogEntity> page = new Page<>(request.getPage(), request.getSize());
        SystemLogQuery systemLogQuery = new SystemLogQuery();
        BeanUtils.copyProperties(request, systemLogQuery);
        systemLogQuery.setUserId(userInfo.getId());
        List<SystemLogEntity> systemLogList = systemLogService.getSystemLogList(page, systemLogQuery);
        page.setRecords(systemLogList);
        return ResultVO.success(getSystemLogListByPage(page));
    }

    private SystemLogListResponse getSystemLogListByPage(IPage<SystemLogEntity> systemLogDOPage) {
        SystemLogListResponse systemLogListResponse = new SystemLogListResponse();
        PageDTOUtil.setPageResponse(systemLogDOPage, systemLogListResponse);
        List<SystemLogListResponse.SystemLogListResponseDetail> list = systemLogDOPage.getRecords().stream().map(record -> {
            SystemLogListResponse.SystemLogListResponseDetail detail = new SystemLogListResponse.SystemLogListResponseDetail();
            detail.setCreateTime(record.getAddTime());
            detail.setFailDesc(record.getFailDesc());
            detail.setActionAddr(record.getActionAddr());
            detail.setResult(record.getResult());
            detail.setHandlerId(record.getUserId());
            detail.setHandlerName(record.getNickname());
            detail.setId(record.getId());
            detail.setDetail(record.getDetail());
            return detail;

        }).collect(Collectors.toList());
        systemLogListResponse.setList(list);
        return systemLogListResponse;
    }
}
