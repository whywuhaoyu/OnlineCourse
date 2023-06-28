package com.why.boot.utils;

import org.apache.hadoop.conf.Configuration;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: HdfsUtil
 * @CreateTime: 2022/4/15 16:29
 */

public class HdfsUtil {

    private static final Configuration conf;
    public static String hdfsURL = "hdfs://localhost:9000";
    static {
        conf=new Configuration();
        conf.set("fs.defaultFS", hdfsURL);
    }

}