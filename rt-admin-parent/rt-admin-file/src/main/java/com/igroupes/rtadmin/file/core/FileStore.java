package com.igroupes.rtadmin.file.core;

import java.io.InputStream;

public interface FileStore {
    /**
     * 上传文件，返回唯一标识
     * @param is
     * @return
     */
    String uploadFile(InputStream is) ;

    /**
     * 根据key删除文件,true:删除成功
     * @param key
     */
    boolean delete(String key) ;

    /**
     * 返回下载地址
     * @param key
     * @return
     */
    String downloadUrl(String key) ;

    InputStream fileStream(String key);

}
