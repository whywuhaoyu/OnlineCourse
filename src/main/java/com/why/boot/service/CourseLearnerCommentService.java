package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.CourseLearnerComment;

import java.util.List;

public interface CourseLearnerCommentService extends IService<CourseLearnerComment> {

    /**
     *
     * @description: 根据用户id获取用户历史评论
     * @param userId 用户id
     * @return: java.util.List<com.why.boot.bean.CourseLearnerComment>
     * @author: why
     * @time: 2023/3/17 19:31
     */
    List<CourseLearnerComment> getCourseLearnerCommentByUserId(Long userId);


    /**
     *
     * @description: 根据用户id，课程id以及评论发表时间删除评论
     * @param courseId 课程id
     * @param userId 用户id
     * @param commentTime 评论发表时间
     * @return: boolean
     * @author: why
     * @time: 2023/3/17 22:37
     */
    boolean deleteMyComment(Long courseId,Long userId,String commentTime);


    /**
     *
     * @description: 根据课程id获取用户评价
     * @param courseId 课程id
     * @return: java.util.List<com.why.boot.bean.CourseLearnerComment>
     * @author: why
     * @time: 2023/3/19 22:53
     */
    List<CourseLearnerComment> getCourseLearnerCommentByCourseId (Long courseId);


    /**
     *
     * @description: 根据课程id获取该课程评分集合
     * @param courseId 课程id
     * @return: java.util.List<java.lang.Object>
     * @author: why
     * @time: 2023/3/24 23:44
     */
    List<Object> getCourseScoreListByCourseId(Long courseId);
}
