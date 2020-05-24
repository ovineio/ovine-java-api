package com.igroupes.rtadmin.service;

import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.SystemLogListRequest;
import com.igroupes.rtadmin.vo.ResultVO;

import javax.validation.Valid;

public interface ISystemLogService {
    ResultVO get(UserInfo userInfo, @Valid SystemLogListRequest request);
}
