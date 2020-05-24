package com.igroupes.rtadmin.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermissionUtils {
    private PermissionUtils() {
    }

    public static RequestPathTrie<Set<String>> apiToPathTrie(String apiDesc) {
        if (StringUtils.isEmpty(apiDesc)) {
            return new RequestPathTrie<>();
        }
        RequestPathTrie<Set<String>> pathTrie = new RequestPathTrie<>();
        Map<String, Set<String>> apiMap = splitApi(apiDesc);
        for (String key : apiMap.keySet()) {
            pathTrie.addPath(key, apiMap.get(key));
        }
        return pathTrie;
    }

    /**
     * 将api分割成请求路径和请求方式列表两个部分
     *
     * @param api
     * @return
     */
    private static Map<String, Set<String>> splitApi(String api) {
        if (StringUtils.isBlank(api)) {
            return MapUtils.EMPTY_MAP;
        }
        Map<String, Set<String>> map = new HashMap<>();
        Iterable<String> apiDescIter = Splitter.on(",").trimResults().omitEmptyStrings().split(api);
        for (String apiInfo : apiDescIter) {
            List<String> apiInfoList = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(apiInfo);
            if (apiInfoList.size() != 2) {
                throw new RtAdminException(ErrorCode.LIMIT_FORMAT_ERROR);
            }
            String path = apiInfoList.get(1);
            if (map.containsKey(path)) {
                Set<String> methodSet = map.get(path);
                methodSet.add(apiInfoList.get(0));
                map.put(path, methodSet);
            } else {
                map.put(path, Sets.newHashSet(apiInfoList.get(0)));
            }
        }
        return map;
    }


    /**
     * @param apiDetail 对应数据库中的权限详情
     * @param request
     * @return 当前用户请求是否校验权限通过
     */
    public static boolean limitPass(String apiDetail, HttpServletRequest request) {
        if (StringUtils.isBlank(apiDetail)) {
            return false;
        }
        if ("*".equals(apiDetail)) {
            return true;
        }
        RequestPathTrie<Set<String>> pathTrie = apiToPathTrie(apiDetail);
        Set<String> requestMethodLSet = pathTrie.find(request.getRequestURI());
        if(CollectionUtils.isEmpty(requestMethodLSet)){
           return false;
        }
        if (requestMethodLSet.contains(request.getMethod().toUpperCase())) {
            return true;
        }
        return false;
    }


    /**
     * ownApi - curApi : 即减少的api权限
     *
     * @param ownApi 当前的api权限
     * @param curApi 设置设置的api权限
     * @return
     */
    public static RequestPathTrie<Set<String>> cutApiPathTrie(String ownApi, String curApi) {
        RequestPathTrie<Set<String>> ownPathTrie = apiToPathTrie(ownApi);
        Map<String, Set<String>> apiMap = splitApi(curApi);
        RequestPathTrie<Set<String>> pathTrie = new RequestPathTrie<>();
        for (String key : apiMap.keySet()) {
            Set<String> apiSet = ownPathTrie.find(key);
            if (ownPathTrie.find(key) == null) {
                pathTrie.addPath(key, apiMap.get(key));
            } else {
                pathTrie.addPath(key, Sets.difference(apiSet, apiMap.get(key)));
            }
        }
        return pathTrie;
    }

    /**
     * limit结构简单直接使用Set表示
     *
     * @param ownLimit
     * @param curLimit
     * @return
     */
    public static Set<String> cutLimitSet(String ownLimit, String curLimit) {
        List<String> ownList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ownLimit);
        List<String> curList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(curLimit);
        Set<String> ret = Sets.newHashSet();
        for (String path : curList) {
            if (!ownList.contains(path)) {
                ret.add(path);
            }
        }
        return ret;
    }

    /**
     * 将子权限减少一部分,并转成字符串表示
     *
     * @param ownApi
     * @return
     */
    public static String cutChildApi(String ownApi, RequestPathTrie<Set<String>> cutApiPathTrie) {
        Map<String, Set<String>> ownApiMap = splitApi(ownApi);
        for (String ownPath : ownApiMap.keySet()) {
            Set<String> requestMethodSet = cutApiPathTrie.find(ownPath);
            if (CollectionUtils.isEmpty(requestMethodSet)) {
                continue;
            }
            Sets.SetView<String> diff = Sets.difference(ownApiMap.get(ownPath), requestMethodSet);
            if (CollectionUtils.isEmpty(diff)) {
                // 如果移除权限之后，没有剩下任何有效的请求方式
                ownApiMap.remove(ownPath);
            } else {
                ownApiMap.put(ownPath, diff);
            }
        }
        return mapToApiStr(ownApiMap);
    }

    /**
     * 将一个map转为api字符串表示
     *
     * @param map
     * @return
     */
    private static String mapToApiStr(Map<String, Set<String>> map) {
        if (MapUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String path : map.keySet()) {
            Set<String> methodSet = map.get(path);
            for (String method : methodSet) {
                stringBuilder.append(method);
                stringBuilder.append(" ");
                stringBuilder.append(path);
                stringBuilder.append(",");
            }
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * 将子权限减少一部分,并转成字符串表示
     *
     * @param ownLimit
     * @param cutLimitSet
     * @return
     */
    public static String cutChildLimit(String ownLimit, Set<String> cutLimitSet) {
        List<String> ownList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ownLimit);
        for (String limit : cutLimitSet) {
            if (ownList.contains(limit)) {
                ownList.remove(limit);
            }
        }
        return limitStr(ownList);
    }

    /**
     * 将limit集合转为字符串表示
     *
     * @param limitList
     * @return
     */
    private static String limitStr(List<String> limitList) {
        if (CollectionUtils.isEmpty(limitList)) {
            return "";
        }
        return Joiner.on(",").skipNulls().join(limitList);
    }


}
