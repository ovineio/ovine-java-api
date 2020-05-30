package com.igroupes.rtadmin.dto.response;

import com.igroupes.rtadmin.dto.PageResponse;
import lombok.Data;

import java.util.List;
@Data
public class RoleListResponse extends PageResponse{
    private List<RoleListResponseDetail> list;


    @Data
    public static class RoleListResponseDetail {
        private Long id;
        private String name;
        private String desc;
        private String createTime;
        private String updateTime;

    }
}
