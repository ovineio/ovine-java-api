package com.igroupes.rtadmin.rpc.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.entity.SystemLoginSessionEntity;
import com.igroupes.rtadmin.entity.SystemUserEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.raw.SystemLoginSessionService;
import com.igroupes.rtadmin.service.raw.SystemUserService;
import com.igroupes.rtadmin.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rpc/user")
public class UserRpcController {
    @Autowired
    private SystemLoginSessionService loginSessionService;
    @Autowired
    private SystemUserService systemUserService;

    @GetMapping("info")
    public ResultVO<UserInfo> infoByToken(String token) {
        SystemLoginSessionEntity loginSessionFind = new SystemLoginSessionEntity();
        if (StringUtils.isBlank(token)) {
            return ResultVO.error(ErrorCode.TOKEN_NOT_EXISTS);
        }
        loginSessionFind.setToken(token);
        SystemLoginSessionEntity loginSession = loginSessionService.getOne(new QueryWrapper<>(loginSessionFind));
        if (loginSession == null) {
            return ResultVO.error(ErrorCode.TOKEN_NOT_EXISTS);
        }
        if (loginSession.getNextExpireTime() < System.currentTimeMillis()) {
            loginSessionService.removeById(loginSession.getId());
            throw new RtAdminException(ErrorCode.TOKEN_EXPIRE);
        }
        Long userId = loginSession.getUserId();
        UserInfo userInfo = new UserInfo();
        SystemUserEntity user = systemUserService.getById(userId);
        if (user == null) {
            return ResultVO.error(ErrorCode.USER_NOT_EXISTS);
        }
        BeanUtils.copyProperties(user, userInfo);
        return ResultVO.success(userInfo);
    }
}
