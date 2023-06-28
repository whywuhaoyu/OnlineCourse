package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysUserNameDuplicatedException
 * @CreateTime: 2023/3/11 12:03
 */

public class SysUserNameDuplicatedException extends ServiceException{
    public SysUserNameDuplicatedException() {
        super();
    }

    public SysUserNameDuplicatedException(String message) {
        super(message);
    }

    public SysUserNameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysUserNameDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected SysUserNameDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}