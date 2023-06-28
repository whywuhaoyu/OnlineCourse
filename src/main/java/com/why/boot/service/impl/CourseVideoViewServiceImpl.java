package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.view.CourseVideoView;
import com.why.boot.mapper.CourseVideoViewMapper;
import com.why.boot.service.CourseVideoViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CourseVideoViewServiceImpl
 * @CreateTime: 2023/3/18 16:50
 */

@Service
public class CourseVideoViewServiceImpl extends ServiceImpl<CourseVideoViewMapper, CourseVideoView> implements CourseVideoViewService {

    @Autowired
   CourseVideoViewMapper courseVideoViewMapper;

    @Override
    public List<CourseVideoView> getCourseVideoByCourseId(Long courseId) {
        QueryWrapper<CourseVideoView> courseVideoViewQueryWrapper=new QueryWrapper<>();
        courseVideoViewQueryWrapper.eq("course_id",courseId);
        return courseVideoViewMapper.selectList(courseVideoViewQueryWrapper);
    }
}