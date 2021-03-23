package com.igroupes.rtadmin.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ConfigRequest {

    @NotBlank
    @Length(max = 2000, message = "内容长度不能超过2000")
    private String content;

    @NotBlank
    @Length(max = 2000, message = "描述长度不能超过2000")
    private String desc;
}
