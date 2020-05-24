package com.igroupes.rtadmin.controller;

import com.igroupes.rtadmin.aop.LoginUser;
import com.igroupes.rtadmin.aop.SkipLogin;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.service.IFileService;
import com.igroupes.rtadmin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 正常情况下，这个接口和file模块是单独放在一个项目中
 */
@RestController
@RequestMapping("file")
@LoginUser
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 同时上传一个文件，返回url
     *
     * @param file
     * @return
     */
    @PostMapping("/image")
    public ResultVO uploadImage(UserInfo userInfo, MultipartFile file, HttpServletRequest request) throws IOException {
        return fileService.uploadImage(userInfo, file.getInputStream(), request);
    }
    @SkipLogin
    @GetMapping("/image/{key}")
    public void getImage(@PathVariable String key, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileService.getImage( key, request, response);
    }


}
