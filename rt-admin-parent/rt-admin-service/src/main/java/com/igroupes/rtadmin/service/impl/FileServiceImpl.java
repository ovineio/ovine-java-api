package com.igroupes.rtadmin.service.impl;

import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.response.FileUploadResponse;
import com.igroupes.rtadmin.entity.FileLogEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.core.FileStore;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.IFileService;
import com.igroupes.rtadmin.service.raw.FileLogService;
import com.igroupes.rtadmin.util.FileTypeUtils;
import com.igroupes.rtadmin.util.HttpUtils;
import com.igroupes.rtadmin.util.StreamUtils;
import com.igroupes.rtadmin.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileStore fileStore;
    @Autowired
    private FileLogService fileLogService;

    @Override
    public ResultVO uploadImage(UserInfo userInfo, InputStream inputStream, HttpServletRequest request) {
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setIp(HttpUtils.getIpAddr(request));
        fileLogEntity.setType(FileLogEntity.TYPE_UPLOAD);
        fileLogEntity.setUserCert(userInfo.getId().toString());
        try {
            inputStream = StreamUtils.supportMarkInputStream(inputStream);
            if (!FileTypeUtils.isImage(inputStream)) {
                return ResultVO.error(ErrorCode.FILE_TYPE_ERROR);
            }
            String fileKey = fileStore.uploadFile(inputStream);
            FileUploadResponse fileUploadResponse = new FileUploadResponse();
            fileUploadResponse.setValue(fileStore.downloadUrl(fileKey));
            ResultVO ret = ResultVO.success(fileUploadResponse);
            fileLogEntity.setResult(RtAdminConstant.RESULT_SUCCESS);
            fileLogService.save(fileLogEntity);
            return ret;
        } catch (Exception ex) {
            log.error("exception:", ex);
            fileLogEntity.setFailDesc(StringUtils.substring(ex.getMessage(), 0, 250));
            fileLogEntity.setResult(RtAdminConstant.RESULT_FAIL);
            fileLogService.save(fileLogEntity);
            throw new RtAdminException(ErrorCode.FILE_UPLOAD_FAIL);
        }

    }

    @Override
    public void getImage( String key, HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setIp(HttpUtils.getIpAddr(request));
        fileLogEntity.setType(FileLogEntity.TYPE_DOWNLOAD);
//        fileLogEntity.setUserCert(userInfo.getId().toString());
        try {
            InputStream inputStream = fileStore.fileStream(key);
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[10 * 1024];
            while (inputStream.read(buffer) != -1) {
                out.write(buffer);
            }
            fileLogEntity.setResult(RtAdminConstant.RESULT_SUCCESS);
            fileLogService.save(fileLogEntity);
        } catch (Exception ex) {
            log.error("exception:", ex);
            fileLogEntity.setFailDesc(StringUtils.substring(ex.getMessage(), 0, 250));
            fileLogEntity.setResult(RtAdminConstant.RESULT_FAIL);
            fileLogService.save(fileLogEntity);
            throw new RtAdminException(ErrorCode.FILE_DOWNLOAD_FAIL);
        }
    }



}
