package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.CourseLearnerComment;
import com.why.boot.mapper.CourseLearnerCommentMapper;
import com.why.boot.service.CourseLearnerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CourseLearnerCommentServiceImpl
 * @CreateTime: 2023/3/17 16:17
 */

@Service
public class CourseLearnerCommentServiceImpl extends ServiceImpl<CourseLearnerCommentMapper, CourseLearnerComment> implements CourseLearnerCommentService {

    @Autowired
    CourseLearnerCommentMapper courseLearnerCommentMapper;

    @Override
    public List<CourseLearnerComment> getCourseLearnerCommentByUserId(Long userId) {
        QueryWrapper<CourseLearnerComment> courseLearnerCommentQueryWrapper=new QueryWrapper<>();
        courseLearnerCommentQueryWrapper.eq("user_id",userId);
        return courseLearnerCommentMapper.selectList(courseLearnerCommentQueryWrapper);
    }

    @Override
    public boolean deleteMyComment(Long courseId, Long userId, String commentTime) {
        QueryWrapper<CourseLearnerComment> courseLearnerCommentQueryWrapper=new QueryWrapper<>();
        courseLearnerCommentQueryWrapper.eq("course_id",courseId).eq("user_id",userId).eq("comment_time",commentTime);
        return courseLearnerCommentMapper.delete(courseLearnerCommentQueryWrapper) == 1;
    }

    @Override
    public List<CourseLearnerComment> getCourseLearnerCommentByCourseId(Long courseId) {
        QueryWrapper<CourseLearnerComment> courseLearnerCommentQueryWrapper=new QueryWrapper<>();
        courseLearnerCommentQueryWrapper.eq("course_id",courseId);
        return courseLearnerCommentMapper.selectList(courseLearnerCommentQueryWrapper);
    }

    @Override
    public List<Object> getCourseScoreListByCourseId(Long courseId) {
        QueryWrapper<CourseLearnerComment> courseLearnerCommentQueryWrapper=new QueryWrapper<>();
        courseLearnerCommentQueryWrapper.eq("course_id",courseId).select("course_score");
        return courseLearnerCommentMapper.selectObjs(courseLearnerCommentQueryWrapper);
    }
}