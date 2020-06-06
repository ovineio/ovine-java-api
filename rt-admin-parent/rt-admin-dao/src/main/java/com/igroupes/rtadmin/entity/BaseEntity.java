package com.igroupes.rtadmin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 通用实体对象
 */
@Data
public class BaseEntity {
    @TableField(fill= FieldFill.INSERT)
    private String addTime;
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String updateTime;
    private Long addUser;
    private Long updateUser;
}
