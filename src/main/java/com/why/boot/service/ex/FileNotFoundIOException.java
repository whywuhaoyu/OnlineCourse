package com.why.boot.service.ex;

import java.io.FileNotFoundException;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: FileNotFoundIOException
 * @CreateTime: 2023/4/28 12:33
 */

public class FileNotFoundIOException extends ServiceException {
    public FileNotFoundIOException() {
        super();
    }

    public FileNotFoundIOException(String message) {
        super(message);
    }

    public FileNotFoundIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundIOException(Throwable cause) {
        super(String.valueOf(cause));
    }

    protected FileNotFoundIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}