package com.igroupes.rtadmin.aop;

import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {


    //使用自定义的注解
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == UserInfo.class;
    }

    //将值注入参数
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return nativeWebRequest.getAttribute(RtAdminConstant.USER_INFO_REQUEST_ATTRIBUTE_KEY, NativeWebRequest.SCOPE_REQUEST);
    }


}

