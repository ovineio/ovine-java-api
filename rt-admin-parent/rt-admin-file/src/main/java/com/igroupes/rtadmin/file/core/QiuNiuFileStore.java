package com.igroupes.rtadmin.file.core;

import com.alibaba.fastjson.JSON;
import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.file.exception.FileStoreException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Slf4j
public class QiuNiuFileStore extends FileStoreAdapter {
    private QiniuFileStoreConfig config;
    private UploadManager uploadManager;
    private Auth auth;
    private StringMap putPolicy;
    private BucketManager bucketManager;
    private static final String DEFAULT_KEY_PREFIX = "file-";
    private static final Integer DEFAULT_MAX_UPLOAD_COUNT = 3;
    private static final Integer DEFAULT_DOWNLOAD_EXPIRE_SECONDS = 24 * 60 * 60;
    private static final Integer DEFAULT_UPLOAD_EXPIRE_SECONDS = 24 * 60 * 60;


    public QiuNiuFileStore(FileStoreConfig config) {
        Configuration configuration = qiniuConfig();
        this.config = checkConfig(config);
        this.auth = Auth.create(this.config.getAccessKey(), this.config.getSecretKey());
        this.uploadManager = new UploadManager(configuration);
        putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        bucketManager = new BucketManager(auth, qiniuConfig());
    }

    private QiniuFileStoreConfig checkConfig(FileStoreConfig config) {
        QiniuFileStoreConfig storeConfig = (QiniuFileStoreConfig) config;
        if (StringUtils.isBlank(storeConfig.getAccessKey()) ||
                StringUtils.isBlank(storeConfig.getSecretKey()) ||
                StringUtils.isBlank(storeConfig.getBucket()) ||
                StringUtils.isBlank(storeConfig.getBucketDomain())) {
            throw new FileStoreException.FileStoreConfigException("qiniu config: accessKey , secretKey , bucket, bucketDomain must provide");
        }
        if (StringUtils.isBlank(storeConfig.getKeyPrefix())) {
            storeConfig.setKeyPrefix(DEFAULT_KEY_PREFIX);
        }
        if (storeConfig.getMaxUploadCount() == null) {
            storeConfig.setMaxUploadCount(DEFAULT_MAX_UPLOAD_COUNT);
        }
        if (storeConfig.getDownloadExpireSeconds() == null) {
            storeConfig.setDownloadExpireSeconds(DEFAULT_DOWNLOAD_EXPIRE_SECONDS);
        }
        if (storeConfig.getUploadExpireSeconds() == null) {
            storeConfig.setUploadExpireSeconds(DEFAULT_UPLOAD_EXPIRE_SECONDS);
        }
        return storeConfig;
    }

    @Override
    public boolean delete(String key) {
        try {
            Response response = bucketManager.delete(config.getBucket(), key);
            int retry = 0;
            while (response.needRetry() && retry++ < config.getMaxUploadCount()) {
                response = bucketManager.delete(config.getBucket(), key);
            }
            return true;
        } catch (Exception ex) {
            log.error("exception:", ex);
            return false;
        }
    }

    @Override
    public String downloadUrl(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String downloadUrl = null;
        try {
            String domainOfBucket = config.getBucketDomain();
            String encodedFileName = URLEncoder.encode(key, RtAdminConstant.URL_DECODE_CHARSET);
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            downloadUrl = auth.privateDownloadUrl(publicUrl, config.getDownloadExpireSeconds());
        } catch (Exception ex) {
            throw new FileStoreException(ex);
        }
        return downloadUrl;
    }


    @Override
    public String uploadFile(InputStream is) {
        String key = getPrefixKey();
        try {
            Response response = uploadManager.put(is, key, getUploadToken(), null, null);
            int retry = 0;
            while (response.needRetry() && retry++ < config.getMaxUploadCount()) {
                response = uploadManager.put(is, key, getUploadToken(), null, null);
            }
            if (response != null) {
                DefaultPutRet defaultPutRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return defaultPutRet.key;
            } else {
                throw new FileStoreException("upload qiniu fail");
            }
        } catch (Exception ex) {
            throw new FileStoreException("upload qiniu fail");
        }
    }


    /**
     * 生成带有前缀的key
     */
    private String getPrefixKey() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        long now = System.currentTimeMillis();
        return config.getKeyPrefix() + "-" + uuid + "-" + now;
    }


    private com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return auth.uploadToken(config.getBucket(), null, config.getUploadExpireSeconds(), putPolicy);
    }


}
