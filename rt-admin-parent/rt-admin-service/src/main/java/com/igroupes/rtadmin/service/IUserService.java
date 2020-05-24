package com.igroupes.rtadmin.service;


import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.*;
import com.igroupes.rtadmin.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;

public interface IUserService  {
    ResultVO login(UserLoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse);

    ResultVO logout(HttpServletRequest request, UserInfo userInfo);

    ResultVO changePassword(HttpServletRequest request, ChangePasswordRequest changePasswordRequest, UserInfo userInfo);

    ResultVO addUser(UserInfo userInfo, UserAddRequest request);

    ResultVO updateUser(UserInfo userInfo, Long userId , UserUpdateRequest request);

    ResultVO deleteUser(UserInfo userInfo, Long id);

    ResultVO getUserList(UserInfo userInfo,UserInfoPageRequest request);


    ResultVO info(UserInfo userInfo);

    ResultVO updateInfo(UserInfo userInfo, UserBaseInfoRequest request);

    BufferedImage verifyCode(HttpServletRequest request, HttpServletResponse response);


    boolean isHigherLevel(Long higherId, Long curId);

    ResultVO userTree(UserInfo userInfo);

    ResultVO demoRegister(@Valid UserRegisterRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse);
}
