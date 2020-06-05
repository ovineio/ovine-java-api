package com.igroupes.rtadmin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_user_stat")
public class SystemUserStatEntity {
    @TableId
    private Long id;
    private Long userId;
    private String ip;
    private Integer operate;
    private String detail;
}
