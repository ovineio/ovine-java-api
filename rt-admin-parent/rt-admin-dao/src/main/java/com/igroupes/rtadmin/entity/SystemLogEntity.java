package com.igroupes.rtadmin.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_log")
public class SystemLogEntity extends BaseEntity {
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_FAIL = 2;
    @TableId
    private Long id;
    private String actionAddr;
    private Long userId;
    private String nickname;
    private Integer result;
    private String failDesc;
    private String detail;
}
