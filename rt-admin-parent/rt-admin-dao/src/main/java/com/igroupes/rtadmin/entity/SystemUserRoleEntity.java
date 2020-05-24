package com.igroupes.rtadmin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_user_role")
public class SystemUserRoleEntity extends BaseEntity {
    @TableId
    private Long id;
    private Long userId;
    private Long roleId;
    @TableLogic // 逻辑删除
    private Integer isDel;
}
