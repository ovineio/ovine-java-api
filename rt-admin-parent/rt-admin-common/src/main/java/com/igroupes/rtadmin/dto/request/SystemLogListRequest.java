package com.igroupes.rtadmin.dto.request;

import com.igroupes.rtadmin.dto.PageRequest;
import lombok.Data;

@Data
public class SystemLogListRequest extends PageRequest{
    private String actionAddr;
    private String handlerFilter;
    private String startTime;
    private String endTime;
    private Integer status;
}
