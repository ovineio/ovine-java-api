package com.igroupes.rtadmin.dto;

import lombok.Data;

@Data
public class PageRequest {
    private Integer page = 0; // 起始数据(表示从0开始的页数)
    private Integer size = 20; // 每页限制大小
}
