package com.why.boot.service.ex;

/**用户账号不存在的异常*/
public class SysUserAccountNotFoundException extends ServiceException {
    public SysUserAccountNotFoundException() {
        super();
    }

    public SysUserAccountNotFoundException(String message) {
        super(message);
    }

    public SysUserAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysUserAccountNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SysUserAccountNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
