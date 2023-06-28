package com.why.boot.service;

import com.why.boot.bean.SysCourse;

import java.util.List;

public interface TopologySortService {


    /**
     *
     * @description:        根据用户专业即课程所属专业获取课程拓扑排序序列

     * @param courseMajor   课程所属专业
     * @return: SysCourse
     * @author: why
     * @time: 2023/4/4 15:39
     */
    List<SysCourse> topologySort(String courseMajor);



}
