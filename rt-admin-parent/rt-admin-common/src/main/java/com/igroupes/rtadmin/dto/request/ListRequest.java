package com.igroupes.rtadmin.dto.request;

import com.igroupes.rtadmin.dto.PageRequest;
import lombok.Data;

/**
 * 这是一个通用的，查询条件是一个
 */
@Data
public class ListRequest extends PageRequest {
    private String key;
    private String startDate;
    private String endDate;
}
