package com.igroupes.rtadmin.file.core;

import lombok.Data;

@Data
public class QiniuFileStoreConfig extends FileStoreConfig {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private Integer maxUploadCount;

    private Integer uploadExpireSeconds;

    private Integer downloadExpireSeconds;

    private String bucketDomain;

    private String keyPrefix; // 前綴
}
