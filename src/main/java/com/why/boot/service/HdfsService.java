package com.why.boot.service;

import java.io.InputStream;

public interface HdfsService {
    /**
     * 初始化加载
     */
    void init();

    /**
     * 判断文件是否存在
     */
    boolean isExisted(String path);

    /**
     * 获取文件输入流
     */
    InputStream getFileInputStream(String hdfsPath);

    /**
     * 文件上传
     */
    boolean fileUpload(InputStream in,String dst);

    boolean deleteFile(String path,boolean recusive);
}
