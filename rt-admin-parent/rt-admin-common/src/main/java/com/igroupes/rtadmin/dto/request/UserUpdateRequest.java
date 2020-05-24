package com.igroupes.rtadmin.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String nickname;
    private String avatar;
    private Long limitId;
    private String desc;
    private String password;
}
