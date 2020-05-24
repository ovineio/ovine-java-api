package com.igroupes.rtadmin.dto.reponse;

import com.igroupes.rtadmin.dto.PageResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoPageResponse extends PageResponse {
    private List<UserInfoPageResponseDetail> list;

    @Data
    public static class UserInfoPageResponseDetail {
        private Long id;
        private String username;
        private String nickname;
        private String avatar;
        private Long limitId;
        private String desc;
        private String createTime;
        private String updateTime;
        private Long parentId;
        private String roleName;
        private Long roleId;
    }

}
