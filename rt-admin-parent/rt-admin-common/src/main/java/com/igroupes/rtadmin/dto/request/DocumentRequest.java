package com.igroupes.rtadmin.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class DocumentRequest {
    @NotBlank
    @Length(max = 30, message = "标题长度不能超过30")
    private String title;

    @NotBlank
    @Length(max = 2000, message = "内容长度不能超过2000")
    private String content;

    @NotBlank
    @Length(max = 2000, message = "描述长度不能超过2000")
    private String desc;
}
