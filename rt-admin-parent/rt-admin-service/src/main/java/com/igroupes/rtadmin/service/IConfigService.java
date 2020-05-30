package com.igroupes.rtadmin.service;

import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.ListRequest;
import com.igroupes.rtadmin.dto.request.ConfigRequest;
import com.igroupes.rtadmin.vo.ResultVO;

public interface IConfigService {

    ResultVO addConfig(UserInfo userInfo, ConfigRequest request);

    ResultVO updateConfig(UserInfo userInfo, Long id , ConfigRequest request);

    ResultVO deleteConfig(UserInfo userInfo, Long id);

    ResultVO getConfigList(UserInfo userInfo, ListRequest request);
}
