package com.igroupes.rtadmin.service;

import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public interface IFileService {
    ResultVO uploadImage(UserInfo userInfo, InputStream inputStream, HttpServletRequest request);

    void getImage(String key, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
