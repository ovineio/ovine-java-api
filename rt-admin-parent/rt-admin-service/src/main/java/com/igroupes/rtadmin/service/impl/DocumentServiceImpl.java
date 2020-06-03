package com.igroupes.rtadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.DocumentRequest;
import com.igroupes.rtadmin.dto.request.ListRequest;
import com.igroupes.rtadmin.dto.response.DocumentListResponse;
import com.igroupes.rtadmin.entity.DocumentEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.IDocumentService;
import com.igroupes.rtadmin.service.raw.DocumentService;
import com.igroupes.rtadmin.util.PageDTOUtil;
import com.igroupes.rtadmin.util.Requires;
import com.igroupes.rtadmin.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    private DocumentService documentService;

    @Transactional
    @Override
    public ResultVO addDocument(UserInfo userInfo, DocumentRequest request) {

        DocumentEntity document = new DocumentEntity();
        BeanUtils.copyProperties(request,document);
        document.setAddUser(userInfo.getId());
        documentService.save(document);
        return ResultVO.success();
    }

    @Transactional
    @Override
    public ResultVO updateDocument(UserInfo userInfo, Long id, DocumentRequest request) {
        DocumentEntity document = checkUserRole(userInfo.getId(),id);
        document.setUpdateUser(userInfo.getId());
//        BeanUtils.copyProperties(request,document);
        document.setContent(request.getContent());
        document.setDesc(request.getDesc());
        document.setTitle(request.getTitle());
        documentService.updateById(document);
        return ResultVO.success();
    }

    @Transactional
    @Override
    public ResultVO deleteDocument(UserInfo userInfo, Long id) {
        checkUserRole(userInfo.getId(),id);
        documentService.removeById(id);
        return ResultVO.success();
    }

    @Override
    public ResultVO getDocumentList(UserInfo userInfo, ListRequest request) {
        Page<DocumentEntity> page = new Page<>(request.getPage(), request.getSize());
        return ResultVO.success(getDocumentListByPage(documentService.findDocumentList(page, userInfo, request)));
    }

    /**
     * 封装返回值
     * @param documentDOPage
     * @return
     */
    private DocumentListResponse getDocumentListByPage(IPage<DocumentEntity> documentDOPage) {
        DocumentListResponse documentListResponse = new DocumentListResponse();
        PageDTOUtil.setPageResponse(documentDOPage, documentListResponse);
        List<DocumentListResponse.DocumentListResponseDetail> list = documentDOPage.getRecords().stream().map(record -> {
            DocumentListResponse.DocumentListResponseDetail detail = new DocumentListResponse.DocumentListResponseDetail(record.getId(),record.getTitle(),record.getContent(),record.getDesc(),record.getAddTime(),record.getUpdateTime());
            return detail;
        }).collect(Collectors.toList());
        documentListResponse.setList(list);
        return documentListResponse;
    }

    /**
     * @param userId 用户id
     * @param id 配置id
     */
    private DocumentEntity checkUserRole(Long userId,Long id) {
        Requires.requireNonNull(userId, "user id");
        Requires.requireNonNull(id, "document id");
        DocumentEntity document = documentService.getById(id);
        if (null == document) {
            throw new RtAdminException(ErrorCode.PARAM_ERROR);
        }
//        if(!document.getAddUser().equals(userId)){
//            throw new RtAdminException(ErrorCode.PERMISSION_DENIED);
//        }
        return document;
    }

}
