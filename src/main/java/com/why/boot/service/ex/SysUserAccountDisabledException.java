package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysUserAccountDisabledException
 * @CreateTime: 2023/3/10 20:45
 */

/**用户账号不可用异常*/
public class SysUserAccountDisabledException extends ServiceException{
    public SysUserAccountDisabledException() {
        super();
    }

    public SysUserAccountDisabledException(String message) {
        super(message);
    }

    public SysUserAccountDisabledException(String message,Throwable cause) {
        super(message, cause);

    }

    protected SysUserAccountDisabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}