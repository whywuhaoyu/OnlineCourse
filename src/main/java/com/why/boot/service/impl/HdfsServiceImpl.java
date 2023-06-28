package com.why.boot.service.impl;

import com.why.boot.service.HdfsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: HdfsServiceImpl
 * @CreateTime: 2023/2/10 19:50
 */
@Slf4j
@Service
public class HdfsServiceImpl implements HdfsService {
    private final Configuration configuration = new Configuration();
    public static String hdfsURL = "hdfs://localhost:9000";
    private FileSystem fileSystem;

    @Override
    @PostConstruct   //Spring初始化执行init方法
    public void init() {
        this.configuration.set("fs.defaultFS", hdfsURL);
        try {
            this.fileSystem = FileSystem.get(configuration);
        } catch (IOException e) {
            log.error("init error",e);
        }
    }

    @Override
    public boolean isExisted(String path) {
        try {
            return fileSystem.exists(new Path(path));
        } catch (IOException e) {
            log.error("isExisted error",e);
        }
        return false;
    }


    @Override
    public InputStream getFileInputStream(String hdfsPath) {
        try {
            // 通过 filesystem.open 方法获取指定路径的文件的输入流
            FSDataInputStream is = fileSystem.open(new Path(hdfsPath));
            return is;
        } catch (IOException e) {
            log.error("getFileInputStream error",e);
        }
        return null;
    }


    @Override
    public boolean fileUpload(InputStream in, String dst) {
        try {
            FSDataOutputStream out = fileSystem.create(new Path(dst), true);
            IOUtils.copyBytes(in, out, configuration);
        } catch (IOException e) {
            log.error("fileUpLoad error",e);
        }
        return isExisted(dst);
    }

    @Override
    public boolean deleteFile(String path, boolean recursive) {
        try {
            return fileSystem.delete(new Path(path), recursive);
        } catch (IOException e) {
            log.error("deleteFile error",e);
        }
        return false;
    }
}