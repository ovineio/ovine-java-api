package com.igroupes.rtadmin.service.impl;

import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.response.StatResponse;
import com.igroupes.rtadmin.dto.request.StatGetRequest;
import com.igroupes.rtadmin.dto.request.StatRequest;
import com.igroupes.rtadmin.entity.SystemUserStatEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.result.StatResult;
import com.igroupes.rtadmin.service.IStatService;
import com.igroupes.rtadmin.service.raw.SystemUserStatService;
import com.igroupes.rtadmin.util.DateUtils;
import com.igroupes.rtadmin.util.EnumUtils;
import com.igroupes.rtadmin.util.HttpUtils;
import com.igroupes.rtadmin.vo.ResultVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements IStatService {
    @Autowired
    private SystemUserStatService systemUserStatService;


    @Override
    public ResultVO addStat(UserInfo userInfo, HttpServletRequest httpRequest, HttpServletResponse httpResponse, StatRequest request) {
        StatRequest.StatCode statCode = EnumUtils.getEnumByCode(StatRequest.StatCode.class, request.getCode());
        if (statCode == null) {
            return ResultVO.error(ErrorCode.STAT_CODE_NOT_EXIST);
        }
        SystemUserStatEntity systemUserStatEntity = new SystemUserStatEntity();
        systemUserStatEntity.setOperate(request.getCode());
        systemUserStatEntity.setDetail(request.getDetail());
        systemUserStatEntity.setIp(HttpUtils.getIpAddr(httpRequest));
        if (userInfo != null) {
            systemUserStatEntity.setUserId(userInfo.getId());
        }
        systemUserStatService.save(systemUserStatEntity);
        return ResultVO.success();
    }

    @Override
    public ResultVO getStat(StatGetRequest request) {
        try {
            if (StringUtils.isNotBlank(request.getStartDate())) {
                request.setStartDate(URLDecoder.decode(request.getStartDate(), RtAdminConstant.URL_DECODE_CHARSET));
            }
            if (StringUtils.isNotBlank(request.getEndDate())) {
                request.setEndDate(URLDecoder.decode(request.getEndDate(), RtAdminConstant.URL_DECODE_CHARSET));
            }
        } catch (Exception ex) {
            return ResultVO.error(ErrorCode.PARAM_ERROR);
        }

        // 默认最近7天
        if (StringUtils.isBlank(request.getStartDate()) && StringUtils.isBlank(request.getEndDate())) {
            request.setEndDate(DateUtils.getDate());
            request.setStartDate(DateUtils.getDate(DateUtils.nextDay(-6), DateUtils.DATE_FORMAT));
        }
        if (StringUtils.isBlank(request.getEndDate())) {
            request.setEndDate(DateUtils.getDate());
        }
        return ResultVO.success(getStatResponse(request));
    }

    private StatResponse getStatResponse(StatGetRequest request) {
        StatResponse statResponse = new StatResponse();
        List<StatResult> stat = fixStat(systemUserStatService.getStat(request.getStartDate(), request.getEndDate()), request.getStartDate(), request.getEndDate());
        List<StatResponse.StatResponseDetail> detail = stat.stream().map(record -> {
            StatResponse.StatResponseDetail statResponseDetail = new StatResponse.StatResponseDetail();
            BeanUtils.copyProperties(record, statResponseDetail);
            return statResponseDetail;
        }).collect(Collectors.toList());
        statResponse.setList(detail);
        return statResponse;
    }


    private List<StatResult> fixStat(List<StatResult> statList, String start, String end) {
        statList = statList.stream().sorted(new Comparator<StatResult>() {
            @Override
            public int compare(StatResult o1, StatResult o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        }).collect(Collectors.toList());
        List<StatResult> ret = new ArrayList<StatResult>();
        if (CollectionUtils.isEmpty(statList)) {
            fillStatData(ret, DateUtils.parseDate(start), DateUtils.addDays(DateUtils.parseDate(end), 1), systemUserStatService.getUserCount(start));
            return ret;
        }
        // statList.get(0).getDate() -> start 这段
        if (!statList.get(0).getDate().equals(start)) {
            fillStatData(ret,DateUtils.parseDate(start), DateUtils.parseDate(statList.get(0).getDate()),  systemUserStatService.getUserCount(statList.get(0).getDate()));
        }

        for (int i = 0; i < statList.size(); i++) {
            StatResult curStat = statList.get(i);
            // 修复第一个
            if (i == 0 ) {
                if(curStat.getUserCount() == 0){
                    statList.get(i).setUserCount(systemUserStatService.getUserCount(curStat.getDate()));
                    ret.add(curStat);
                }
                continue;
            }

            StatResult preStat = statList.get(i - 1);
            if (curStat.getUserCount() == 0) {
                curStat.setUserCount(preStat.getUserCount());
            }
            Date curDate = DateUtils.parseDate(curStat.getDate());
            Date preDate = DateUtils.parseDate(preStat.getDate());
            if (DateUtils.getDaysBetween(preDate, curDate) == 1) {
                ret.add(curStat);
                continue;
            }
            // 补充这几天的数据
            fillStatData(ret, preDate, curDate, preStat.getUserCount());
            ret.add(curStat);
        }
        // -> end
        if (!statList.get(statList.size() - 1).getDate().equals(end)) {
            fillStatData(ret, DateUtils.parseDate(statList.get(statList.size() - 1).getDate()), DateUtils.parseDate(end), statList.get(statList.size() - 1).getUserCount());
        }
        return ret;
    }

    /**
     * @param data
     * @param start
     * @param end       不包含
     * @param userCount
     */
    private void fillStatData(List<StatResult> data, Date start, Date end, int userCount) {
        while (!DateUtils.formatDate(start).equals(DateUtils.formatDate(end))) {
            data.add(new StatResult(DateUtils.formatDate(start), 0, 0, userCount, 0, 0));
            start = DateUtils.addDays(start, 1);
        }
    }

}
