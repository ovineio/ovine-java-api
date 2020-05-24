package com.igroupes.rtadmin.file.core;

import java.io.InputStream;

public class FileStoreAdapter implements FileStore {

    @Override
    public String uploadFile(InputStream is) {
        throw new UnsupportedOperationException("upload file");
    }

    @Override
    public boolean delete(String key) {
        throw new UnsupportedOperationException("delete");
    }

    @Override
    public String downloadUrl(String key) {
        throw new UnsupportedOperationException("download url");
    }

    @Override
    public InputStream fileStream(String key) {
        throw new UnsupportedOperationException("file stream");
    }

}
