package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.LearnerCourseVideoRelation;

import java.util.List;

public interface LearnerCourseVideoRelationService extends IService<LearnerCourseVideoRelation> {


    /**
     *
     * @description:     根据用户id和课程id删除用户、课程与视频的关联关系

     * @param userId     用户id
     * @param courseId   课程id
     * @return: boolean
     * @author: why
     * @time: 2023/3/31 21:48
     */
    boolean deleteLearnerCourseVideoRelationByUserId(Long userId,Long courseId);


    /**
     *
     * @description:        根据用户id、课程id以及视频id修改视频的播放位置和是否看完标识

     * @param userId        用户id
     * @param courseId      课程id
     * @param videoId       视频id
     * @param currentTime   视频播放位置
     * @param isDone        视频是否已看完
     * @return: boolean
     * @author: why
     * @time: 2023/4/1 12:03
     */
    boolean updateVideoCurrentAndIsDone(Long userId,Long courseId,Long videoId,String currentTime,boolean isDone);


    /**
     *
     * @description:         根据用户id、课程id以及视频id修改视频的播放位置和是否看完标识

     * @param userId         用户id
     * @param courseId       课程id
     * @param videoId        视频id
     * @param videoDuration  视频时长
     * @param currentTime    视频播放位置
     * @return: boolean
     * @author: why
     * @time: 2023/4/1 12:22
     */
    boolean updateVideoCurrent(Long userId,Long courseId,Long videoId,String videoDuration,String currentTime);



    /**
     *
     * @description:       判断视频是否已播放完

     * @param userId       用户id
     * @param courseId     课程id
     * @param videoId      视频id
     * @return: boolean
     * @author: why
     * @time: 2023/4/1 12:24
     */
    boolean videoIsDone(Long userId,Long courseId,Long videoId);


    /**
     *
     * @description:       根据用户id、课程id以及视频id获取视频信息
     *
     * @param userId       用户id
     * @param courseId     课程id
     * @param videoId      视频id
     * @return: com.why.boot.bean.LearnerCourseVideoRelation
     * @author: why
     * @time: 2023/4/1 14:23
     */
    LearnerCourseVideoRelation getLearnerCourseVideoRelationByVideoId(Long userId,Long courseId,Long videoId);



    /**
     *
     * @description:      根据课程id和用户id获取所有关联视频的时长集合

     * @param courseId    课程id
     * @param userId      用户id
     * @return: java.util.List<java.lang.Object>
     * @author: why
     * @time: 2023/4/1 20:09
     */
    List<Object> getAllVideoDurationByCourseIdAndUserId(Long courseId,Long userId);



    /**
     *
     * @description:     根据课程id和用户id获取已完成视频的时长集合

     * @param courseId   课程id
     * @param userId     用户id
     * @return: java.util.List<java.lang.Object>
     * @author: why
     * @time: 2023/4/1 20:12
     */
    List<Object> getVideoDurationByIsDone(Long courseId,Long userId);



    /**
     *
     * @description:      根据课程id和用户id获取未完成视频的进度集合

     * @param courseId    课程id
     * @param userId      用户ig
     * @return: java.util.List<java.lang.Object>
     * @author: why
     * @time: 2023/4/1 20:13
     */
    List<Object> getVideoCurrentByIsNotDone(Long courseId,Long userId);



    /**
     *
     * @description:       设置自动连播，播放完最后一条视频，将从头开始播放，会将已播放完的视频进度设为初始值

     * @param courseId     课程id
     * @param userId       用户id
     * @return: boolean
     * @author: why
     * @time: 2023/4/3 14:11
     */
    boolean upateIsDoneVideoCurrentToInitial(Long courseId,Long userId);
}
