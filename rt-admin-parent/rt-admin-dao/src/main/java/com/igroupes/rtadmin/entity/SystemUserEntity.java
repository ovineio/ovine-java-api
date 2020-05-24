package com.igroupes.rtadmin.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("system_user")
public class SystemUserEntity extends BaseEntity {
    @TableId
    private Long id;
    private String username; //登录用户名
    private String nickname;
    private String avatar;
    private String password;
    private String salt;//加密密码的盐
    private Long parentId;
    private String desc;
    private String signature;
    private String parentChain; // 父级用户链
    @TableLogic // 逻辑删除
    private Integer isDel;
}
