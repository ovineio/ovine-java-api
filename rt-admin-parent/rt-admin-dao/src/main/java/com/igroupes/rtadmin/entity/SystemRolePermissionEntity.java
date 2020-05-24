package com.igroupes.rtadmin.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_role_perm")
public class SystemRolePermissionEntity extends BaseEntity  {
    @TableId
    private Long id;
    private Long permId;
    private Long roleId;
    @TableLogic // 逻辑删除
    private Integer isDel;
}
