package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.CourseLearnerStudy;

import java.util.List;

public interface CourseLearnerStudyService extends IService<CourseLearnerStudy> {


    /**
     *
     * @description: 根据用户id获取用户已收藏课程集合
     * @param userId 用户is
     * @return: java.util.List<com.why.boot.bean.CourseLearnerStudy>
     * @author: why
     * @time: 2023/3/17 13:17
     */
    List<CourseLearnerStudy> getCourseLearnerStudyListByUserId(Long userId);


    /**
     *
     * @description: 根据用户id以及课程id删除用户收藏课程
     * @param courseId 课程id
     * @param userId 用户id
     * @return: boolean
     * @author: why
     * @time: 2023/3/17 23:40
     */
    boolean deleteMyStudy(Long courseId,Long userId);

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


    /**
     *
     * @description: 根据课程id和用户id修改最后观看视频以及时间

     * @param courseId 课程id
     * @param userId   用户id
     * @param videId   视频id
     * @return: boolean
     * @author: why
     * @time: 2023/3/31 20:01
     */
    boolean updateLastWatchVideoByCourseIdAndUserId(Long courseId,Long userId,Long videId);



    /**
     *
     * @description:           根据课程id和用户id修改课程进度

     * @param courseId         课程id
     * @param userId           用户id
     * @param courseProgress   课程进度
     * @return: boolean
     * @author: why
     * @time: 2023/4/1 20:41
     */
    boolean updateCourseProgressByCourseIdAndUserId(Long courseId,Long userId,String courseProgress);


    /**
     *
     * @description:            根据用户id、课程id获取用户课程信息

     * @param userId            用户id
     * @param courseId          课程id
     * @return: com.why.boot.bean.CourseLearnerStudy
     * @author: why
     * @time: 2023/4/6 11:37
     */
    CourseLearnerStudy getCourseLearnerStudyByCourseIdAndUserId(Long userId,Long courseId);


    /**
     *
     * @description:            根据用户id、课程id修改用户课程成绩

     * @param userId            用户id
     * @param courseId          课程id
     * @param score             课程成绩
     * @return: boolean
     * @author: why
     * @time: 2023/5/3 21:35
     */
    boolean updateUserGradeByCourseIdAndUserId(Long userId,Long courseId,int score);
}
