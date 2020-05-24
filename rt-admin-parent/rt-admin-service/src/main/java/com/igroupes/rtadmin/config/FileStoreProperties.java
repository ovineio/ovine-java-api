package com.igroupes.rtadmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rtadmin.file-store")
public class FileStoreProperties {
    private String fileStoreDir; // 文件存储目录
    private long storeDirMaxBytes; // 存储目录的最大字节大小
    private String downloadUrlPrefix; // 下载的url前缀
    private String type;
}
