package com.igroupes.rtadmin.dto.response;

import com.igroupes.rtadmin.dto.PageResponse;
import lombok.Data;

import java.util.List;

@Data
public class ConfigListResponse extends PageResponse {
    List<ConfigListResponseDetail> list;

    @Data
    public static class ConfigListResponseDetail{
        private Long id;
        private String content;
        private String desc;
        private String CreateTime;
        private String updateTime;
    }
}
