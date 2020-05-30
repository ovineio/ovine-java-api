package com.igroupes.rtadmin.dto.response;

import com.igroupes.rtadmin.dto.PageResponse;
import lombok.Data;

import java.util.List;

@Data
public class SystemLogListResponse extends PageResponse {
    private List<SystemLogListResponseDetail> list;


    @Data
    public static class SystemLogListResponseDetail {
        private Long id;
        private String actionAddr;
        private Long handlerId;
        private String handlerName;
        private Integer result;
        private String failDesc;
        private String createTime;
        private String detail;
    }
}
