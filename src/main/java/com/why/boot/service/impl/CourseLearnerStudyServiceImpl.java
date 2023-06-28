package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.bcel.internal.generic.ConstantPushInstruction;
import com.why.boot.bean.CourseLearnerStudy;
import com.why.boot.mapper.CourseLearnerStudyMapper;
import com.why.boot.service.CourseLearnerStudyService;
import com.why.boot.service.SysVideoService;
import com.why.boot.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CourseLearnerStudyServiceImpl
 * @CreateTime: 2023/3/17 9:11
 */

@Service
public class CourseLearnerStudyServiceImpl extends ServiceImpl<CourseLearnerStudyMapper, CourseLearnerStudy> implements CourseLearnerStudyService {

    @Autowired
    CourseLearnerStudyMapper courseLearnerStudyMapper;

    @Autowired
    SysVideoService sysVideoService;

    @Override
    public List<CourseLearnerStudy> getCourseLearnerStudyListByUserId(Long userId) {
        QueryWrapper<CourseLearnerStudy> courseLearnerStudyQueryWrapper=new QueryWrapper<>();
        courseLearnerStudyQueryWrapper.eq("user_id",userId);
        return courseLearnerStudyMapper.selectList(courseLearnerStudyQueryWrapper);
    }

    @Override
    public boolean deleteMyStudy(Long courseId, Long userId) {
        QueryWrapper<CourseLearnerStudy> courseLearnerStudyQueryWrapper=new QueryWrapper<>();
        courseLearnerStudyQueryWrapper.eq("course_id",courseId).eq("user_id",userId);
        return courseLearnerStudyMapper.delete(courseLearnerStudyQueryWrapper)==1;
    }

    @Override
    public boolean isExistedCollectCourse(Long courseId, Long userId) {
        QueryWrapper<CourseLearnerStudy> courseLearnerStudyQueryWrapper=new QueryWrapper<>();
        courseLearnerStudyQueryWrapper.eq("course_id",courseId).eq("user_id",userId);
        return !StringUtils.isEmpty(courseLearnerStudyMapper.selectOne(courseLearnerStudyQueryWrapper));
    }

    @Override
    public boolean updateLastWatchVideoByCourseIdAndUserId(Long courseId, Long userId,Long videoId) {
        UpdateWrapper<CourseLearnerStudy> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("course_id",courseId).eq("user_id",userId)
                .set("video_id",videoId).set("video_name",sysVideoService.getById(videoId).getVideoName())
                .set("last_watch_time", WebUtil.dateToStrong(new Date(),WebUtil.DATETIME));
        return courseLearnerStudyMapper.update(null,updateWrapper) == 1;
    }

    @Override
    public boolean updateCourseProgressByCourseIdAndUserId(Long courseId, Long userId, String courseProgress) {
        UpdateWrapper<CourseLearnerStudy> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("course_id",courseId).eq("user_id",userId)
                .set("course_progress",courseProgress);
        return courseLearnerStudyMapper.update(null,updateWrapper) == 1;
    }

    @Override
    public CourseLearnerStudy getCourseLearnerStudyByCourseIdAndUserId(Long userId, Long courseId) {
        QueryWrapper<CourseLearnerStudy> courseLearnerStudyQueryWrapper=new QueryWrapper<>();
        courseLearnerStudyQueryWrapper.eq("user_id",userId).eq("course_id",courseId);
        return courseLearnerStudyMapper.selectOne(courseLearnerStudyQueryWrapper);
    }

    @Override
    public boolean updateUserGradeByCourseIdAndUserId(Long userId, Long courseId, int score) {
        UpdateWrapper<CourseLearnerStudy> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("course_id",courseId).eq("user_id",userId)
                .set("grade",score);
        return courseLearnerStudyMapper.update(null,updateWrapper) == 1;
    }
}