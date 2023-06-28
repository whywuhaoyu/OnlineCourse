package com.why.boot.service.ex;

/**用户名已经被占用的异常*/
public class SysUserAccountDuplicatedException extends ServiceException{
    public SysUserAccountDuplicatedException() {
        super();
    }

    public SysUserAccountDuplicatedException(String message) {
        super(message);
    }

    public SysUserAccountDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysUserAccountDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected SysUserAccountDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
