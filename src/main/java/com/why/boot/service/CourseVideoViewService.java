package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.view.CourseVideoView;

import java.util.List;

public interface CourseVideoViewService extends IService<CourseVideoView> {

    /**
     *
     * @description: 根据课程id获取课程视频集合
     * @param courseId 课程id
     * @return: java.util.List<com.why.boot.bean.view.CourseVideoView>
     * @author: why
     * @time: 2023/3/18 22:33
     */
    List<CourseVideoView> getCourseVideoByCourseId(Long courseId);

}
