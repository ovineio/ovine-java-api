package com.igroupes.rtadmin.file.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.igroupes.rtadmin.file.exception.FileStoreException;
import com.igroupes.rtadmin.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SimpleFileStore extends FileStoreAdapter {
    private SimpleFileStoreConfig config;
    private static final long DEFAULT_STORE_FILE_MAX_BYTES = 10 * 1024 * 1024 * 1024; // 10G
    private static final String DEFAULT_FILE_PREFIX_NAME = "file-"; // 10G
    private static final String SCHEMA_FILE_NAME = "file-store.schema"; // key->md5的json格式文件
    // 一个md5对应多个文件
    private static final ConcurrentHashMap<String, List<String>> MD5_KEY_SCHEMA_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> KEY_MD5_SCHEMA_MAP = new ConcurrentHashMap<>();


    public SimpleFileStore(FileStoreConfig config) {
        this.config = checkConfig(config);
        // 加载元数据文件
        loadSchemaData();
    }

    private void loadSchemaData() {
        File file = new File(getSchemaFilePath());
        if (!file.exists()) {
            // 不存在就创建
            try {
                boolean create = file.createNewFile();
                if (!create) {
                    throw new FileStoreException.FileStoreConfigException(String.format("schema file : %s create fail", getSchemaFilePath()));
                }
            } catch (Exception ex) {
                log.error("exception:", ex);
                throw new FileStoreException.FileStoreConfigException(String.format("schema file : %s create fail", getSchemaFilePath()));
            }
        } else {
            // 存在就加载
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                if (stringBuilder.length() == 0) {
                    return;
                }
                Map<String, String> map = JSONObject.parseObject(stringBuilder.toString(), Map.class);
                KEY_MD5_SCHEMA_MAP.putAll(map);
                for (String key : KEY_MD5_SCHEMA_MAP.keySet()) {
                    String md5 = KEY_MD5_SCHEMA_MAP.get(key);
                    List<String> keyList = MD5_KEY_SCHEMA_MAP.get(md5);
                    if (CollectionUtils.isEmpty(keyList)) {
                        MD5_KEY_SCHEMA_MAP.put(md5, Lists.newArrayList(key));
                    } else {
                        keyList.add(key);
                        MD5_KEY_SCHEMA_MAP.put(md5, keyList);
                    }
                }
            } catch (Exception ex) {
                log.error("exception:", ex);
                throw new FileStoreException.FileStoreConfigException("schema load fail");
            }
        }
    }

    private SimpleFileStoreConfig checkConfig(FileStoreConfig config) {
        SimpleFileStoreConfig storeConfig = (SimpleFileStoreConfig) config;
        if (storeConfig == null) {
            throw new FileStoreException.FileStoreConfigException("config is null");
        }
        if (StringUtils.isBlank(storeConfig.getFileStoreDir())) {
            throw new FileStoreException.FileStoreConfigException("file store directory is blank");
        }
        if (storeConfig.getStoreDirMaxBytes() < 0) {
            log.warn("file store directory max bytes less 0 , so set default value ");
            storeConfig.setStoreDirMaxBytes(DEFAULT_STORE_FILE_MAX_BYTES);
        }
        if (storeConfig.getStoreDirMaxBytes() == 0) {
            storeConfig.setStoreDirMaxBytes(DEFAULT_STORE_FILE_MAX_BYTES);
        }
        if (StringUtils.isBlank(storeConfig.getFilePrefixName())) {
            storeConfig.setFilePrefixName(DEFAULT_FILE_PREFIX_NAME);
        }
        if (StringUtils.isBlank(storeConfig.getDownloadUrlPrefix())) {
            log.error("download url prefix is blank");
            throw new FileStoreException.FileStoreConfigException("download url prefix is blank");
        }
        return storeConfig;
    }

    @Override
    public String uploadFile(InputStream is) {
        Long key = null;
        File file = null;
        try {
            ByteArrayInputStream in = (ByteArrayInputStream) is;
            byte[] bytes = getBytes(in);
            String md5 = Md5Utils.computeMd5(bytes);
            List<String> existKeyList = MD5_KEY_SCHEMA_MAP.get(md5);
            if (CollectionUtils.isNotEmpty(existKeyList)) {
                for (String existKey : existKeyList) {
                    byte[] b = ByteUtils.getByStream(new FileInputStream(getFileName(Long.parseLong(existKey))));
                    if (ByteUtils.isSame(b, bytes)) {
                        return existKey;
                    }
                }
            }

            // 判断文件夹是不是已经达到最大容量
            long size = FileUtils.sizeOfDirectory(new File(config.getFileStoreDir()));
            if (size >= config.getStoreDirMaxBytes()) {
                throw new FileStoreException.FileStoreOutOfSizeException(String.format("size : %s is bigger than max size :%s", size, config.getStoreDirMaxBytes()));
            }

            key = UniqueKeyUtils.getKey();
            // 查找有效文件
            while ((file = new File(getFileName(key))).exists()) {
                key = UniqueKeyUtils.getKey();
            }
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(bytes, 0, bytes.length);
            fout.flush();
            fout.close();
            // 更新元数据
            putSchema(md5, key.toString(), file);
        } catch (Exception ex) {
            try {
                if (file.exists()) {
                    FileUtils.forceDelete(file);
                }
            } catch (Exception e) {
                log.error("exception:", e);
            }
            throw new FileStoreException(ex);
        }

        return key.toString();
    }


    private byte[] getBytes(ByteArrayInputStream bais) throws NoSuchFieldException, IllegalAccessException {
        Field bufField = bais.getClass().getDeclaredField("buf");
        bufField.setAccessible(true);
        return (byte[]) bufField.get(bais);
    }


    private void writeSchemaData(File file) throws IOException {

        try {
            AtomicFileOutputStream fout = new AtomicFileOutputStream(new File(getSchemaFilePath()));
            fout.write(JSON.toJSONString(KEY_MD5_SCHEMA_MAP).getBytes(Charset.defaultCharset()));
            fout.close();
        } catch (Exception ex) {
            try {
                if (file.exists()) {
                    FileUtils.forceDelete(file);
                }
            } catch (Exception e) {
                log.error("exception:", e);
            }
            throw ex;
        }
    }

    /**
     * @param md5
     * @param key
     * @param srcFile 如果元数据文件更新失败，需要删除srcFile
     */
    private void putSchema(String md5, String key, File srcFile) throws IOException {
        synchronized (md5) {
            List<String> keys = MD5_KEY_SCHEMA_MAP.get(md5);
            if (CollectionUtils.isEmpty(keys)) {
                MD5_KEY_SCHEMA_MAP.put(md5, Lists.newArrayList(key));
            } else {
                keys.add(key);
                MD5_KEY_SCHEMA_MAP.put(md5, keys);
            }
        }
        KEY_MD5_SCHEMA_MAP.put(key, md5);
        writeSchemaData(srcFile);
    }

    @Override
    public boolean delete(String key) {
        File f = checkKeyFile(key);
        return f.delete();
    }

    @Override
    public String downloadUrl(String key) {
        checkKeyFile(key);
        String trimDownloadUrlPrefix = config.getDownloadUrlPrefix().trim();
        if (trimDownloadUrlPrefix.endsWith("/")) {
            return trimDownloadUrlPrefix + key;
        } else {
            return trimDownloadUrlPrefix + "/" + key;
        }
    }

    @Override
    public InputStream fileStream(String key) {
        try {
            return new FileInputStream(checkKeyFile(key));
        } catch (Exception ex) {
            throw new FileStoreException(ex);
        }
    }

    private String getSchemaFilePath() {
        return FileUtils.getFile(config.getFileStoreDir(), SCHEMA_FILE_NAME).getAbsolutePath();
    }

    private String getFileName(long key) {
        if (key == 0) {
            throw new FileStoreException.FileStoreKeyException("key is 0");
        }
        return FileUtils.getFile(config.getFileStoreDir(), config.getFilePrefixName() + key).getAbsolutePath();
    }


    private File checkKeyFile(String key) {
        if (StringUtils.isBlank(key)) {
            throw new FileStoreException.FileStoreKeyException("key is blank");
        }
        String fileName = getFileName(Long.parseLong(key));
        File f = new File(fileName);
        boolean filePresent = f.exists();
        if (!filePresent) {
            throw new FileStoreException.FileStoreKeyException("key is invalid");
        }
        return f;
    }
}
