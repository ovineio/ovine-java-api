package com.igroupes.rtadmin.aop;

import com.alibaba.fastjson.JSON;
import com.igroupes.rtadmin.file.exception.BusinessException;
import com.igroupes.rtadmin.util.ExceptionUtils;
import com.igroupes.rtadmin.util.HttpUtils;
import com.igroupes.rtadmin.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
        log.error("》》》》请求地址：{}",request.getRequestURI());
        log.error("》》》》请求IP：{}", HttpUtils.getIpAddr(request));
        log.error("》》》》请求方式：{}", request.getMethod());
        log.error("》》》》请求参数：{}", JSON.toJSONString(request.getParameterMap()));
        log.error("》》》》异常信息：", exception);

        BusinessException businessException = ExceptionUtils.warp(exception);
        return ResultVO.error(businessException.getMessage(), businessException.getCode());
    }

}
