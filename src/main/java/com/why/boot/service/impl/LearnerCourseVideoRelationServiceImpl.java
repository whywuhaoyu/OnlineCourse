package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.LearnerCourseVideoRelation;
import com.why.boot.mapper.LearnerCourseVideoRelationMapper;
import com.why.boot.service.LearnerCourseVideoRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: LearnerCourseVideoRelationServiceImpl
 * @CreateTime: 2023/3/31 16:32
 */

@Service
public class LearnerCourseVideoRelationServiceImpl extends ServiceImpl<LearnerCourseVideoRelationMapper, LearnerCourseVideoRelation> implements LearnerCourseVideoRelationService {

    @Autowired
    LearnerCourseVideoRelationMapper learnerCourseVideoRelationMapper;

    @Override
    public boolean deleteLearnerCourseVideoRelationByUserId(Long userId,Long courseId) {
        QueryWrapper<LearnerCourseVideoRelation> courseVideoRelationQueryWrapper=new QueryWrapper<>();
        courseVideoRelationQueryWrapper.eq("user_id",userId).eq("course_id",courseId);
        return learnerCourseVideoRelationMapper.delete(courseVideoRelationQueryWrapper)==1;
    }

    @Override
    public boolean updateVideoCurrentAndIsDone(Long userId, Long courseId, Long videoId, String currentTime, boolean isDone) {
        UpdateWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationUpdateWrapper=new UpdateWrapper<>();
        learnerCourseVideoRelationUpdateWrapper.eq("user_id",userId).eq("course_id",courseId)
                                               .eq("video_id",videoId).set("video_current",currentTime).set("is_done",isDone);
        return learnerCourseVideoRelationMapper.update(null,learnerCourseVideoRelationUpdateWrapper) == 1;
    }

    @Override
    public boolean updateVideoCurrent(Long userId, Long courseId, Long videoId, String videoDuration,String currentTime) {
        UpdateWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationUpdateWrapper=new UpdateWrapper<>();
        learnerCourseVideoRelationUpdateWrapper.eq("user_id",userId).eq("course_id",courseId)
                                               .eq("video_id",videoId).set("video_duration",videoDuration)
                                               .set("video_current",currentTime);
        return learnerCourseVideoRelationMapper.update(null,learnerCourseVideoRelationUpdateWrapper) == 1;
    }

    @Override
    public boolean videoIsDone(Long userId, Long courseId, Long videoId) {
        QueryWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationQueryWrapper=new QueryWrapper<>();
        learnerCourseVideoRelationQueryWrapper.eq("user_id",userId).eq("course_id",courseId) .eq("video_id",videoId);
        return learnerCourseVideoRelationMapper.selectOne(learnerCourseVideoRelationQueryWrapper).isDone();
    }

    @Override
    public LearnerCourseVideoRelation getLearnerCourseVideoRelationByVideoId(Long userId,Long courseId,Long videoId) {
        QueryWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationQueryWrapper=new QueryWrapper<>();
        learnerCourseVideoRelationQueryWrapper.eq("user_id",userId).eq("course_id",courseId) .eq("video_id",videoId);
        return learnerCourseVideoRelationMapper.selectOne(learnerCourseVideoRelationQueryWrapper);
    }

    @Override
    public List<Object> getAllVideoDurationByCourseIdAndUserId(Long courseId, Long userId) {
        QueryWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationQueryWrapper=new QueryWrapper<>();
        learnerCourseVideoRelationQueryWrapper.eq("course_id",courseId).eq("user_id",userId).select("video_duration");
        return learnerCourseVideoRelationMapper.selectObjs(learnerCourseVideoRelationQueryWrapper);
    }

    @Override
    public List<Object> getVideoDurationByIsDone(Long courseId, Long userId) {
        QueryWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationQueryWrapper=new QueryWrapper<>();
        learnerCourseVideoRelationQueryWrapper.eq("course_id",courseId).eq("user_id",userId)
                .eq("is_done",1).select("video_duration");
        return learnerCourseVideoRelationMapper.selectObjs(learnerCourseVideoRelationQueryWrapper);
    }

    @Override
    public List<Object> getVideoCurrentByIsNotDone(Long courseId, Long userId) {
        QueryWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationQueryWrapper=new QueryWrapper<>();
        learnerCourseVideoRelationQueryWrapper.eq("course_id",courseId).eq("user_id",userId)
                .eq("is_done",0).select("video_current");
        return learnerCourseVideoRelationMapper.selectObjs(learnerCourseVideoRelationQueryWrapper);
    }

    @Override
    public boolean upateIsDoneVideoCurrentToInitial(Long courseId,Long userId) {
        UpdateWrapper<LearnerCourseVideoRelation> learnerCourseVideoRelationUpdateWrapper=new UpdateWrapper<>();
        learnerCourseVideoRelationUpdateWrapper.eq("course_id",courseId).eq("user_id",userId)
                .eq("is_done",1).set("video_current","00:00:00");
        return learnerCourseVideoRelationMapper.update(null,learnerCourseVideoRelationUpdateWrapper) == 1;
    }
}