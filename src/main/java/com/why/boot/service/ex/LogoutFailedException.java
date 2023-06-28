package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: LogoutFailedException
 * @CreateTime: 2023/3/17 11:28
 */

public class LogoutFailedException extends ServiceException{
    public LogoutFailedException() {
        super();
    }

    public LogoutFailedException(String message) {
        super(message);
    }

    public LogoutFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogoutFailedException(Throwable cause) {
        super(cause);
    }

    protected LogoutFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}