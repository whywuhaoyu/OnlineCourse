package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: FileType
 * @CreateTime: 2023/4/28 12:40
 */

public class FileTypeNotImageException extends ServiceException{
    public FileTypeNotImageException() {
        super();
    }

    public FileTypeNotImageException(String message) {
        super(message);
    }

    public FileTypeNotImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeNotImageException(Throwable cause) {
        super(cause);
    }

    protected FileTypeNotImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}