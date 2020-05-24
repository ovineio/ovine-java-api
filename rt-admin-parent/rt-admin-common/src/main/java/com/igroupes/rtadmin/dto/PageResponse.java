package com.igroupes.rtadmin.dto;

import lombok.Data;

@Data
public class PageResponse {
    private Long count; // 总个数
    private Long page; // 当前页数
    private Long size; // 每页数量

}
