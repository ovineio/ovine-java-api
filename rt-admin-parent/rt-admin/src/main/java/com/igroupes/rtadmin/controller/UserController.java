package com.igroupes.rtadmin.controller;


import com.igroupes.rtadmin.aop.LoginUser;
import com.igroupes.rtadmin.aop.SkipLogin;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.ChangePasswordRequest;
import com.igroupes.rtadmin.dto.request.UserBaseInfoRequest;
import com.igroupes.rtadmin.dto.request.UserLoginRequest;
import com.igroupes.rtadmin.dto.request.UserRegisterRequest;
import com.igroupes.rtadmin.service.IUserService;
import com.igroupes.rtadmin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("user")
@LoginUser
public class UserController {
    @Autowired
    private IUserService userService;

    @SkipLogin
    @PostMapping("login")
    public ResultVO login(@Valid @RequestBody UserLoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        return userService.login(request,httpRequest,httpResponse);
    }

    @SkipLogin
    @RequestMapping(value="code",produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage verifyCode(HttpServletRequest request, HttpServletResponse response) {
        return userService.verifyCode(request,response);
    }

    @PostMapping("logout")
    public ResultVO logout(UserInfo userInfo, HttpServletRequest request) {
        return userService.logout(request, userInfo);
    }

    @GetMapping("info")
    public ResultVO info(UserInfo userInfo) {
        return userService.info(userInfo);
    }


    @PutMapping("info")
    public ResultVO updateInfo(UserInfo userInfo, @Valid @RequestBody UserBaseInfoRequest request) {
        return userService.updateInfo(userInfo,request);
    }

    /**
     * 修改密碼
     *
     * @param userInfo
     * @param request
     * @return
     */
    @PutMapping("password")
    public ResultVO changePassword(HttpServletRequest request,  UserInfo userInfo,  @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(request, changePasswordRequest, userInfo);
    }

    @SkipLogin
    @PostMapping("demo_register")
    public ResultVO demoRegister(@Valid @RequestBody UserRegisterRequest request,HttpServletRequest httpRequest, HttpServletResponse httpResponse){
       return userService.demoRegister(request,httpRequest,httpResponse);
    }
}
