package com.igroupes.rtadmin.dto.request;

import com.igroupes.rtadmin.dto.PageRequest;
import lombok.Data;

@Data
public class UserInfoPageRequest extends PageRequest {
    private String filter; // 关键字查询。模糊匹配 ID/名称/登录账号
    private String roleIds; // 角色id
}
