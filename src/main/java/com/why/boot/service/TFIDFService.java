package com.why.boot.service;

import com.why.boot.bean.SysCourse;

import java.util.List;

public interface TFIDFService {

    /**
     *
     * @description:     使用TF-IDF内容推荐算法，为用户进行课程推荐

     * @param userId     用户id
     * @return: java.util.List<com.why.boot.bean.SysCourse>
     * @author: why
     * @time: 2023/4/21 20:34
     */
    List<SysCourse> TFIDFRecommend(Long userId);
}
