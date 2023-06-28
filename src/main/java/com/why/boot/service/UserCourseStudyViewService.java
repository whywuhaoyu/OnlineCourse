package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.view.UserCourseStudyView;

public interface UserCourseStudyViewService extends IService<UserCourseStudyView> {

    /**
     *
     * @description: 根据用户id与课程id判断课程是否已被收藏
     * @param courseId
     * @param userId
     * @return: boolean
     * @author: why
     * @time: 2023/3/16 22:36
     */
    boolean isExistedCollectCourse(Long courseId,Long userId);
}
