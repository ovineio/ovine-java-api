package com.igroupes.rtadmin.service;

import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.StatGetRequest;
import com.igroupes.rtadmin.dto.request.StatRequest;
import com.igroupes.rtadmin.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IStatService {
    ResultVO addStat(UserInfo userInfo, HttpServletRequest httpRequest, HttpServletResponse httpResponse, StatRequest request);

    ResultVO getStat(StatGetRequest request);
}
