package com.igroupes.rtadmin.entity;

import lombok.Data;

/**
 * 通用实体对象
 */
@Data
public class BaseEntity {
    private String addTime;
    private String updateTime;
    private Long addUser;
    private Long updateUser;
}
