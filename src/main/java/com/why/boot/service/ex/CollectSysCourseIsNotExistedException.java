package com.why.boot.service.ex;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: AddSysCourseIsExistedException
 * @CreateTime: 2023/3/16 22:23
 */

public class CollectSysCourseIsNotExistedException extends ServiceException{
    public CollectSysCourseIsNotExistedException() {
        super();
    }

    public CollectSysCourseIsNotExistedException(String message) {
        super(message);
    }

    public CollectSysCourseIsNotExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectSysCourseIsNotExistedException(Throwable cause) {
        super(cause);
    }

    protected CollectSysCourseIsNotExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}