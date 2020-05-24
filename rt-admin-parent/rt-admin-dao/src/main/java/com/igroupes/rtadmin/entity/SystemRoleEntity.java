package com.igroupes.rtadmin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_role")
public class SystemRoleEntity extends BaseEntity {
    @TableId
    private Long id;
    private String name;
    private String desc;
    private Long parentId;
    private String parentChain;
    @TableLogic // 逻辑删除
    private Integer isDel;
}
