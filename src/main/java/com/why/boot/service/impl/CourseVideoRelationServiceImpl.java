package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.CourseVideoRelation;
import com.why.boot.mapper.CourseVideoRelationMapper;
import com.why.boot.service.CourseVideoRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CourseVideoRelationServiceImpl
 * @CreateTime: 2023/3/20 9:31
 */

@Service
public class CourseVideoRelationServiceImpl extends ServiceImpl<CourseVideoRelationMapper, CourseVideoRelation> implements CourseVideoRelationService {

    @Autowired
    CourseVideoRelationMapper courseVideoRelationMapper;

    @Override
    public List<CourseVideoRelation> getCourseVideoByCourseId(Long courseId) {
        QueryWrapper<CourseVideoRelation> courseVideoRelationQueryWrapper=new QueryWrapper<>();
        courseVideoRelationQueryWrapper.eq("course_id",courseId);
        return courseVideoRelationMapper.selectList(courseVideoRelationQueryWrapper);
    }
}