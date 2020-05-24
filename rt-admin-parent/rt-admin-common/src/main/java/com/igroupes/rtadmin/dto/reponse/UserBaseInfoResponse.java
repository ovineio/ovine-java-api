package com.igroupes.rtadmin.dto.reponse;

import lombok.Data;

@Data
public class UserBaseInfoResponse {
    private String avatar;
    private String nickname;
    private String signature;
    private String limit;
    private Long parentId;
    private String parentNickname;
}
