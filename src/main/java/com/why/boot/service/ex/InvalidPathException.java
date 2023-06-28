package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: InvalidPathException
 * @CreateTime: 2023/4/28 12:30
 */

public class InvalidPathException extends ServiceException{
    public InvalidPathException() {
        super();
    }

    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPathException(Throwable cause) {
        super(cause);
    }

    protected InvalidPathException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}