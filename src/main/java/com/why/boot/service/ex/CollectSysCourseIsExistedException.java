package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: AddSysCourseIsExistedException
 * @CreateTime: 2023/3/16 22:23
 */

public class CollectSysCourseIsExistedException extends ServiceException{
    public CollectSysCourseIsExistedException() {
        super();
    }

    public CollectSysCourseIsExistedException(String message) {
        super(message);
    }

    public CollectSysCourseIsExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectSysCourseIsExistedException(Throwable cause) {
        super(cause);
    }

    protected CollectSysCourseIsExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}