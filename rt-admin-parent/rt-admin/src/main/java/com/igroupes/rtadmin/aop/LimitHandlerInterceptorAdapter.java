package com.igroupes.rtadmin.aop;

import com.igroupes.rtadmin.config.RequestProperties;
import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.raw.SystemPermissionService;
import com.igroupes.rtadmin.util.PermissionUtils;
import com.igroupes.rtadmin.util.Requires;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Slf4j
public class LimitHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
    private SystemPermissionService systemPermissionService;
    private RequestProperties requestProperties;

    public LimitHandlerInterceptorAdapter(SystemPermissionService systemPermissionService, RequestProperties requestProperties) {
        this.systemPermissionService = systemPermissionService;
        this.requestProperties = requestProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (needLimit(request)) {
            UserInfo userInfo = (UserInfo) request.getAttribute(RtAdminConstant.USER_INFO_REQUEST_ATTRIBUTE_KEY);
            Requires.requireNonNull(userInfo, "user info");
            Requires.requireNonNull(userInfo.getId(), "user id");
            SystemPermissionEntity permissionDO = systemPermissionService.getPermissionById(userInfo.getId());
            log.info("用户：{} 拥有的权限api列表:{},本次需要检查的api:{}", userInfo.getId() , permissionDO.getApi(),request.getRequestURI());
            boolean pass = PermissionUtils.limitPass(permissionDO.getApi(), request);
            log.info("用户：{} ，{}当前api:{}的权限",userInfo.getId() ,  pass ? "有":"没有" , request.getRequestURI());
            if (!pass) {
                throw new RtAdminException(ErrorCode.PERMISSION_DENIED);
            }
            return super.preHandle(request, response, handler);
        }

        return true;
    }


    private boolean needLimit(HttpServletRequest request) {
        return StringUtils.isBlank(requestProperties.getLimitExcludePath()) || !Pattern.matches(requestProperties.getLimitExcludePath(), request.getServletPath());
    }

}
