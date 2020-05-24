package com.igroupes.rtadmin.dto.reponse;

import lombok.Data;

import java.util.List;

@Data
public class UserTreeResponse {
    private String nickname;
    private Long id;
    private List<UserTreeResponse> children;

    public UserTreeResponse(Long id){
        this.id =id;
    }
    public UserTreeResponse(Long id,String nickname){
        this.id =id;
        this.nickname = nickname;
    }
    public UserTreeResponse(){
    }
}
