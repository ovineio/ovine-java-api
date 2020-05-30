package com.igroupes.rtadmin.dto.response;

import com.igroupes.rtadmin.dto.PageResponse;
import lombok.Data;

import java.util.List;

@Data
public class DocumentListResponse extends PageResponse {
    List<DocumentListResponseDetail> list;

    @Data
    public static class DocumentListResponseDetail{
        private Long id;
        private String title;
        private String content;
        private String desc;
        private String CreateTime;
        private String updateTime;

        public DocumentListResponseDetail(Long id, String title, String content, String desc, String createTime, String updateTime) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.desc = desc;
            CreateTime = createTime;
            this.updateTime = updateTime;
        }
    }
}
