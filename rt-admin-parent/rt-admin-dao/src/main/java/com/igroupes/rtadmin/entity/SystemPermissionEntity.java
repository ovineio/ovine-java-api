package com.igroupes.rtadmin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_perm")
public class SystemPermissionEntity extends BaseEntity {
    @TableId
    private Long id;
    private String limitDetail;
    private String api;
    @TableLogic // 逻辑删除
    private Integer isDel;
}
