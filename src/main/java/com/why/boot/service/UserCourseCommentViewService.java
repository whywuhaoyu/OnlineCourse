package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.view.UserCourseCommentView;

import java.util.List;

public interface UserCourseCommentViewService extends IService<UserCourseCommentView> {

    /**
     *
     * @description: 根据课程id获取用户评价
     * @param courseId 课程id
     * @return: com.why.boot.bean.view.UserCourseCommentView
     * @author: why
     * @time: 2023/3/15 19:33
     */
    List<UserCourseCommentView> getCourseCommentByCourseId(Long courseId);

}
