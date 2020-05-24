package com.igroupes.rtadmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("file_log")
public class FileLogEntity extends BaseEntity {
    public static final int TYPE_UPLOAD = 1;
    public static final int TYPE_DOWNLOAD = 2;

    private String ip;
    private String userCert;
    private Integer type;
    private String failDesc;
    private Integer result;
}
