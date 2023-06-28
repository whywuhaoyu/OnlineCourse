package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.CourseVideoRelation;

import java.util.List;

public interface CourseVideoRelationService extends IService<CourseVideoRelation> {

    /**
     *
     * @description: 根据课程id获取课程视频集合
     * @param courseId 课程id
     * @return: java.util.List<com.why.boot.bean.CourseVideoRelation>
     * @author: why
     * @time: 2023/3/20 9:33
     */
    List<CourseVideoRelation> getCourseVideoByCourseId(Long courseId);
}
