package com.igroupes.rtadmin.controller;

import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.StatGetRequest;
import com.igroupes.rtadmin.dto.request.StatRequest;
import com.igroupes.rtadmin.service.IStatService;
import com.igroupes.rtadmin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("stat")
public class StatController {
    @Autowired
    private IStatService statService;


    @PostMapping("data")
    public ResultVO addStat(UserInfo userInfo, HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody @Valid StatRequest request) {
        return statService.addStat(userInfo, httpRequest, httpResponse, request);
    }

    @GetMapping("data")
    public ResultVO getData(StatGetRequest request) {
        return statService.getStat(request);
    }
}
