package com.igroupes.rtadmin.file.core;

import lombok.Data;

@Data
public class SimpleFileStoreConfig extends FileStoreConfig {
    private String fileStoreDir; // 文件存储目录
    private long storeDirMaxBytes; // 存储目录的最大字节大小
    private String filePrefixName; // 文件的前缀名
    private String downloadUrlPrefix; // 下载的url前缀
}
