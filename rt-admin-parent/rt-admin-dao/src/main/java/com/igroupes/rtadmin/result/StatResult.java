package com.igroupes.rtadmin.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatResult {
    private String date;
    private Integer loginCount;
    private Integer registerCount;
    private Integer userCount;
    private Integer showCount;
    private Integer showUserCount;
}
