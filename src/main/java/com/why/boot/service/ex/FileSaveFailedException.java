package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: FileSaveFailedException
 * @CreateTime: 2023/4/28 12:37
 */

public class FileSaveFailedException extends ServiceException{
    public FileSaveFailedException() {
        super();
    }

    public FileSaveFailedException(String message) {
        super(message);
    }

    public FileSaveFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSaveFailedException(Throwable cause) {
        super(String.valueOf(cause));
    }

    protected FileSaveFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}