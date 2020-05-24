package com.igroupes.rtadmin.file.core;

import com.igroupes.rtadmin.file.exception.FileStoreException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;

public class FileStoreFactory {
    private static final FileStoreFactory STORE_FACTORY = new FileStoreFactory();

    public static FileStoreFactory instance() {
        return STORE_FACTORY;
    }

    public FileStore fileStore(String className, FileStoreConfig config) {
        if(StringUtils.isBlank(className)){
          throw new FileStoreException("className is blank , can't create file store");
        }

        Constructor<? extends FileStore> constructor = null;
        try {
            Class fileStoreClazz = Class.forName(className);
            constructor = fileStoreClazz.getConstructor(FileStoreConfig.class);
            return constructor.newInstance(config);
        } catch (Exception ex) {
           throw new FileStoreException(ex);
        }
    }

}
