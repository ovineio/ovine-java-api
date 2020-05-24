package com.igroupes.rtadmin.dto.reponse;

import lombok.Data;

import java.util.List;

@Data
public class StatResponse {
    private List<StatResponseDetail> list;

    @Data
    public static class StatResponseDetail {
        private String date;
        private Integer loginCount;
        private Integer registerCount;
        private Integer userCount;
        private Integer showCount;
        private Integer showUserCount;
    }

}
