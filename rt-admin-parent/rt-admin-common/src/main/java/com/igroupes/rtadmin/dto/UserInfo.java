package com.igroupes.rtadmin.dto;

import lombok.Data;

/**
 * 包含当前用户的基本信息
 */
@Data
public class UserInfo {
    private Long id;
    private String username; //登录用户名
    private String nickname;
    private String avatar;
    private String password;
    private String salt;//加密密码的盐
    private Integer isDel;
    private String desc;
    private String signature;
    private Long parentId;
    private String parentChain;
    private String addTime;
    private String updateTime;
    private Long addUser;
    private Long updateUser;
}
