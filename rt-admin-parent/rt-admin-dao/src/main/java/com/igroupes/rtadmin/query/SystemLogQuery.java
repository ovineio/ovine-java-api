package com.igroupes.rtadmin.query;

import lombok.Data;

@Data
public class SystemLogQuery {
    private String actionAddr;
    private String handlerFilter;
    private String startTime;
    private String endTime;
    private Integer status;
    private Long userId;
}
