package com.igroupes.rtadmin.service;

import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.request.DocumentRequest;
import com.igroupes.rtadmin.dto.request.ListRequest;
import com.igroupes.rtadmin.vo.ResultVO;

public interface IDocumentService {

    ResultVO addDocument(UserInfo userInfo, DocumentRequest request);

    ResultVO updateDocument(UserInfo userInfo, Long id, DocumentRequest request);

    ResultVO deleteDocument(UserInfo userInfo, Long id);

    ResultVO getDocumentList(UserInfo userInfo, ListRequest request);
}
