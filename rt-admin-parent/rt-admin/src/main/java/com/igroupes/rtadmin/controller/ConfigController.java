package com.igroupes.rtadmin.controller;

import com.igroupes.rtadmin.aop.LoginUser;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.ListRequest;
import com.igroupes.rtadmin.dto.request.ConfigRequest;
import com.igroupes.rtadmin.service.IConfigService;
import com.igroupes.rtadmin.util.PageDTOUtil;
import com.igroupes.rtadmin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("config")
@LoginUser
public class ConfigController {

    @Autowired
    private IConfigService configService;

    @PostMapping("item")
    public ResultVO addConfig(UserInfo userInfo, @Valid @RequestBody ConfigRequest request) {
        return configService.addConfig(userInfo,request);
    }

    @PutMapping("item/{id}")
    public ResultVO updateConfig(UserInfo userInfo, @PathVariable Long id, @Valid @RequestBody ConfigRequest request) {
        return configService.updateConfig(userInfo, id, request);
    }

    @DeleteMapping("item/{id}")
    public ResultVO deleteConfig(UserInfo userInfo, @PathVariable Long id) {
        return configService.deleteConfig(userInfo, id);
    }
    @GetMapping("item")
    public ResultVO getConfigs(UserInfo userInfo, ListRequest request){
        PageDTOUtil.fixPageDTO(request);
        return configService.getConfigList(userInfo, request);
    }

}
