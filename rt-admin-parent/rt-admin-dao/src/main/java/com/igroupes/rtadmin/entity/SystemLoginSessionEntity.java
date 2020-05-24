package com.igroupes.rtadmin.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_login_session")
public class SystemLoginSessionEntity extends BaseEntity {
    @TableId
    private Long id;
    private Long userId;
    private String token;
    private Long nextExpireTime;

}
