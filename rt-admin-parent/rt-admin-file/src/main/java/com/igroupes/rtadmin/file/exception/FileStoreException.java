package com.igroupes.rtadmin.file.exception;

public class FileStoreException extends RuntimeException{
    public FileStoreException(String message) {
        super(message);
    }
    public FileStoreException(String message, Throwable cause) {
        super(message, cause);
    }
    public FileStoreException(Throwable cause) {
        super(cause);
    }

    public static class FileStoreConfigException extends FileStoreException{
        public FileStoreConfigException(String message) {
            super(message);
        }
    }

    public static class FileStoreKeyException extends FileStoreException{
        public FileStoreKeyException(String message) {
            super(message);
        }
    }

    public static class FileStoreOutOfSizeException extends FileStoreException{
        public FileStoreOutOfSizeException(String message) {
            super(message);
        }
    }
}
