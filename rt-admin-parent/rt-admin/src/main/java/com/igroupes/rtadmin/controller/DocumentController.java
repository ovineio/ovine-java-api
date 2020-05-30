package com.igroupes.rtadmin.controller;

import com.igroupes.rtadmin.aop.LoginUser;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.DocumentRequest;
import com.igroupes.rtadmin.dto.request.ListRequest;
import com.igroupes.rtadmin.service.IDocumentService;
import com.igroupes.rtadmin.util.PageDTOUtil;
import com.igroupes.rtadmin.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("document")
@LoginUser
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

    @PostMapping("item")
    public ResultVO addDocument(UserInfo userInfo, @Valid @RequestBody DocumentRequest request) {
        return documentService.addDocument(userInfo,request);
    }

    @PutMapping("item/{id}")
    public ResultVO updateDocument(UserInfo userInfo, @PathVariable Long id, @Valid @RequestBody DocumentRequest request) {
        return documentService.updateDocument(userInfo, id, request);
    }

    @DeleteMapping("item/{id}")
    public ResultVO deleteDocument(UserInfo userInfo, @PathVariable Long id) {
        return documentService.deleteDocument(userInfo, id);
    }
    @GetMapping("items")
    public ResultVO getDocuments(UserInfo userInfo, ListRequest request){
        PageDTOUtil.fixPageDTO(request);
        return documentService.getDocumentList(userInfo, request);
    }

}
