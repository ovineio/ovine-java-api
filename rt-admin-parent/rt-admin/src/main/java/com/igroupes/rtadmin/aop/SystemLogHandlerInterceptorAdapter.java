package com.igroupes.rtadmin.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.igroupes.rtadmin.config.RequestProperties;
import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.entity.SystemLogEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.filter.HttpRequestWrapper;
import com.igroupes.rtadmin.filter.HttpResponseWrapper;
import com.igroupes.rtadmin.service.raw.SystemLogService;
import com.igroupes.rtadmin.util.ExceptionUtils;
import com.igroupes.rtadmin.util.RequestPathTrie;
import com.igroupes.rtadmin.vo.ResultVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class SystemLogHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
    private static final String ALL_REQUEST_METHOD = "*";
    private SystemLogService systemLogService;
    private RequestProperties requestProperties;
    private RequestPathTrie<List<String>> logExcludePathTrie;
    private RequestPathTrie<RequestMethodField> logRecordExcludeFieldPathTrie;

    public SystemLogHandlerInterceptorAdapter(SystemLogService systemLogService, RequestProperties requestProperties) {
        this.systemLogService = systemLogService;
        this.requestProperties = requestProperties;
        // 放在requestProperties后面
        this.logExcludePathTrie = logExcludePathTrie();
        this.logRecordExcludeFieldPathTrie = logRecordExcludeFieldPathTrie();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfo userInfo = (UserInfo) request.getAttribute(RtAdminConstant.USER_INFO_REQUEST_ATTRIBUTE_KEY);
        if (userInfo == null) {
            return;
        }
        String requestMethod = request.getMethod().toUpperCase();
        // 检查请求类型
        if (!Pattern.matches(requestProperties.getLogRequestTypeFilter(), requestMethod)) {
            log.info("请求类型 ：{} 不需要记录系统日志 ", requestMethod);
            return;
        }
        // 检查请求路径和方法
        List<String> pathMethodList = this.logExcludePathTrie.find(request.getServletPath());
        if(containsRequestMethod(request,pathMethodList)){
            log.info("请求路径:{},请求方法:{}不需要记录系统日志", request.getServletPath(), requestMethod);
            return;
        }
        SystemLogEntity systemLogDO = new SystemLogEntity();

        String logPath = request.getHeader(RtAdminConstant.SYSTEM_LOG_PATH_HEADER_KEY);
        if (StringUtils.isBlank(logPath)) {
            systemLogDO.setActionAddr("");
        } else {
            systemLogDO.setActionAddr(logPath);
        }
        systemLogDO.setUserId(userInfo.getId());
        systemLogDO.setNickname(userInfo.getNickname());
        setSystemLogError(systemLogDO, ex, response);

        // 添加detail
        systemLogDO.setDetail(getRequestParamDetail(request));
        systemLogService.save(systemLogDO);
        super.afterCompletion(request, response, handler, ex);
    }

    private void setSystemLogError(SystemLogEntity systemLogEntity, Exception ex, HttpServletResponse response) {
        // 1:success , 0:fail
        systemLogEntity.setResult(SystemLogEntity.RESULT_SUCCESS);
        if (ex != null) {
            systemLogEntity.setResult(SystemLogEntity.RESULT_FAIL);
            systemLogEntity.setFailDesc(ExceptionUtils.warp(ex).getMessage());
        }
        // 解析响应内容
        HttpResponseWrapper responseWrapper = (HttpResponseWrapper) response;
        try {
            if (isJSONContentType(responseWrapper.getContentType())) {
                String ret = new String(responseWrapper.getContent(), responseWrapper.getCharacterEncoding());
                ResultVO resultVO = JSONObject.parseObject(ret, ResultVO.class);
                if (resultVO.getCode() == ResultVO.CODE_OK) {
                    systemLogEntity.setResult(SystemLogEntity.RESULT_SUCCESS);
                } else {
                    systemLogEntity.setResult(SystemLogEntity.RESULT_FAIL);
                    systemLogEntity.setFailDesc(resultVO.getMsg());
                }
            }
        } catch (Exception e) {
            // skip
        }
    }

    private boolean containsRequestMethod(HttpServletRequest request, List<String> requestMethodList) {
        if (CollectionUtils.isEmpty(requestMethodList)) {
            return false;
        }
        if(requestMethodList.size() == 1 && requestMethodList.get(0).equals(ALL_REQUEST_METHOD)){
            // 处理*通配符
           return true;
        }
        for (String pathMethod : requestMethodList) {
            if (pathMethod.equals(request.getMethod().toUpperCase())) {
                return true;
            }
        }
        return false;
    }


    private RequestPathTrie<List<String>> logExcludePathTrie() {
        RequestPathTrie<List<String>> pathTrie = new RequestPathTrie<>();
        String logExcludePath = requestProperties.getLogExcludePath();
        for (String path : trimStr(logExcludePath, ";")) {
            List<String> pathMethod = trimStr(path, ":");
            if (pathMethod.size() < 2) {
                throw new RtAdminException(ErrorCode.CONF_FORMAT_EXCEPTION);
            }
            pathTrie.addPath(pathMethod.get(0), normalizeRequestMethod(pathMethod.get(1)));
        }
        return pathTrie;
    }


    private RequestPathTrie<RequestMethodField> logRecordExcludeFieldPathTrie() {
        RequestPathTrie<RequestMethodField> pathTrie = new RequestPathTrie<RequestMethodField>();
        for (String path : trimStr(requestProperties.getLogRecordExcludeField(), ";")) {
            List<String> pathMethodField = trimStr(path, ":");
            if (pathMethodField.size() < 3) {
                throw new RtAdminException(ErrorCode.CONF_FORMAT_EXCEPTION);
            }
            RequestMethodField requestMethodField = new RequestMethodField();
            requestMethodField.setRequestMethod(normalizeRequestMethod(pathMethodField.get(1)));
            requestMethodField.setRequestField(trimStr(pathMethodField.get(2), ","));
            pathTrie.addPath(pathMethodField.get(0), requestMethodField);
        }
        return pathTrie;
    }

    /**
     * 通过逗号分隔请求方式，如果请求方式中含有*,直接返回*
     *
     * @param requestMethodStr 多个中间使用逗号隔开
     * @return
     */
    private List<String> normalizeRequestMethod(String requestMethodStr) {
        List<String> list = Lists.newArrayList();
        List<String> methodList = trimStr(requestMethodStr, ",");
        for (String requestMethod : methodList) {
            // 处理通配
            if (requestMethod.contains("*")) {
                if (requestMethod.length() == 1) {
                    return Lists.newArrayList(ALL_REQUEST_METHOD);
                } else {
                    log.error("except :* , but : {}", requestMethod);
                }
            }
            list.add(requestMethod.toUpperCase());
        }
        return list;
    }

    /**
     * 字符串分隔
     *
     * @return
     */
    private List<String> trimStr(String str, String separatorChars) {
        if (StringUtils.isBlank(str)) {
            return ListUtils.EMPTY_LIST;
        }
        List<String> ret = Lists.newArrayList();
        String[] splits = StringUtils.split(str, separatorChars);
        for (String s : splits) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            ret.add(s.trim());
        }
        return ret;
    }

    @Data
    private static class RequestMethodField {
        private List<String> requestMethod;
        private List<String> requestField;
    }


    private String getRequestParamDetail(HttpServletRequest request) {
        Map<String, String> requestParamMap = new HashMap<String, String>();
        requestParamMap.put("_request_path", request.getServletPath());
        requestParamMap.put("_request_method", request.getMethod());
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            for (String key : parameterMap.keySet()) {
                requestParamMap.put(key, Arrays.toString(parameterMap.get(key)));
            }
        } else {
            if (isJSONContentType(request.getContentType())) {
                Map jsonBodyMap = JSONObject.parseObject(((HttpRequestWrapper) request).getBodyString());
                RequestMethodField requestMethodField = this.logRecordExcludeFieldPathTrie.find(request.getServletPath());
                if (requestMethodField != null && containsRequestMethod(request, requestMethodField.getRequestMethod()) && CollectionUtils.isNotEmpty(requestMethodField.getRequestField())) {
                   // 需要去掉部分字段
                    for (Object key : jsonBodyMap.keySet()) {
                        if(!requestMethodField.getRequestField().contains(key)){
                            requestParamMap.put(key.toString(), jsonBodyMap.get(key).toString());
                        }
                    }
                }else{
                    for (Object key : jsonBodyMap.keySet()) {
                        requestParamMap.put(key.toString(), jsonBodyMap.get(key).toString());
                    }
                }
            }
        }
        return JSON.toJSONString(requestParamMap);
    }


    private boolean isJSONContentType(String contentType) {
        return contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }
}
