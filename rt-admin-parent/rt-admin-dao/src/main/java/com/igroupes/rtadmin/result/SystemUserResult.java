package com.igroupes.rtadmin.result;

import lombok.Data;

@Data
public class SystemUserResult {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private Long limitId;
    private String desc;
    private String addTime;
    private String updateTime;
    private Long addUser;
    private Long parentId;
    private String roleName;
    private Long roleId;
}
