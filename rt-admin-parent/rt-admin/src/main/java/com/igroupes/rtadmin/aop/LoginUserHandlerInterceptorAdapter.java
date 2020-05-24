package com.igroupes.rtadmin.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.igroupes.rtadmin.config.LoginProperties;
import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.entity.SystemLoginSessionEntity;
import com.igroupes.rtadmin.entity.SystemUserEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.raw.SystemLoginSessionService;
import com.igroupes.rtadmin.service.raw.SystemUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUserHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private LoginProperties loginProperties;
    private SystemLoginSessionService loginSessionService;
    private SystemUserService systemUserService;

    public LoginUserHandlerInterceptorAdapter(LoginProperties loginProperties, SystemLoginSessionService loginSessionService, SystemUserService systemUserService) {
        this.loginProperties = loginProperties;
        this.loginSessionService = loginSessionService;
        this.systemUserService = systemUserService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader(loginProperties.getTokenKey());
        // 获取用户信息
        Exception tmpEx = null;
        try {
            request.setAttribute(RtAdminConstant.USER_INFO_REQUEST_ATTRIBUTE_KEY, getUserInfo(token));
        } catch (Exception ex) {
            tmpEx = ex;
        }
        LoginUser typeAnno = handlerMethod.getBeanType().getAnnotation(LoginUser.class);
        // 如果需要登录，但是没有获取到用户信息
        if (typeAnno != null) {
            SkipLogin skipLogin = handlerMethod.getMethodAnnotation(SkipLogin.class);
            if (skipLogin == null && tmpEx != null) {
               throw tmpEx;
            }
        }else{
            LoginUser methodAnno = handlerMethod.getMethodAnnotation(LoginUser.class);
            if (methodAnno != null && tmpEx != null) {
                throw tmpEx;
            }
        }
        return true;
    }


    private UserInfo getUserInfo(String token) {
        SystemLoginSessionEntity loginSessionFind = new SystemLoginSessionEntity();
        if (StringUtils.isBlank(token)) {
            throw new RtAdminException(ErrorCode.TOKEN_NOT_EXISTS);
        }
        loginSessionFind.setToken(token);
        SystemLoginSessionEntity loginSession = loginSessionService.getOne(new QueryWrapper<>(loginSessionFind));
        if (loginSession == null) {
            throw new RtAdminException(ErrorCode.TOKEN_NOT_EXISTS);
        }
        if (loginSession.getNextExpireTime() < System.currentTimeMillis()) {
            loginSessionService.removeById(loginSession.getId());
            throw new RtAdminException(ErrorCode.TOKEN_EXPIRE);
        }
        Long userId = loginSession.getUserId();
        UserInfo userInfo = new UserInfo();
        SystemUserEntity user = systemUserService.getById(userId);
        if (user == null) {
            throw new RtAdminException(ErrorCode.USER_NOT_EXISTS);
        }
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }


}
