package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.SysVideo;

public interface SysVideoService extends IService<SysVideo> {


    /**
     *
     * @description: 根据视频id获取视频时长

     * @param videoId 视频id
     * @return: java.lang.String
     * @author: why
     * @time: 2023/3/31 19:17
     */
    String getVideoDurationByVideoId(Long videoId);
}
