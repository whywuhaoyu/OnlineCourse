package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.CoursePrerequisite;
import com.why.boot.bean.SysCourse;
import com.why.boot.mapper.CoursePrerequisiteMapper;
import com.why.boot.mapper.SysCourseMapper;
import com.why.boot.service.CoursePrerequisiteService;
import com.why.boot.service.SysCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CoursePrerequisiteServiceImpl
 * @CreateTime: 2023/4/4 14:42
 */

@Service
public class CoursePrerequisiteServiceImpl extends ServiceImpl<CoursePrerequisiteMapper, CoursePrerequisite> implements CoursePrerequisiteService {

    @Autowired
    CoursePrerequisiteMapper coursePrerequisiteMapper;

    @Autowired
    SysCourseMapper sysCourseMapper;


    @Autowired
    SysCourseService sysCourseService;


    @Override
    public Integer getInDegree(Long courseId) {
        QueryWrapper<CoursePrerequisite> coursePrerequisiteQueryWrapper=new QueryWrapper<>();
        coursePrerequisiteQueryWrapper.eq("course_id",courseId);
        return coursePrerequisiteMapper.selectCount(coursePrerequisiteQueryWrapper);
    }

    @Override
    public List<Long> getInDegreeList(Long courseId) {
        QueryWrapper<CoursePrerequisite> coursePrerequisiteQueryWrapper=new QueryWrapper<>();
        coursePrerequisiteQueryWrapper.eq("course_id",courseId).select("prerequisite_id");
        List<Object> objectList=coursePrerequisiteMapper.selectObjs(coursePrerequisiteQueryWrapper);
        List<Long> longList=new ArrayList<>();
        for(Object object:objectList){
            longList.add(Long.valueOf(object.toString()));
        }
        return longList;
    }

    @Override
    public List<SysCourse> getInDegreeCourseList(Long courseId) {
        QueryWrapper<CoursePrerequisite> coursePrerequisiteQueryWrapper=new QueryWrapper<>();
        coursePrerequisiteQueryWrapper.eq("course_id",courseId).select("prerequisite_id");
        List<Object> objectList=coursePrerequisiteMapper.selectObjs(coursePrerequisiteQueryWrapper);
        List<SysCourse> sysCourseList=new ArrayList<>();
        for(Object object:objectList){
            sysCourseList.add(sysCourseService.getCourseByCourseId(Long.valueOf(object.toString())));
        }
        return sysCourseList;
    }

    @Override
    public List<Long> getOutDegreeList(Long courseId) {
        QueryWrapper<CoursePrerequisite> coursePrerequisiteQueryWrapper=new QueryWrapper<>();
        coursePrerequisiteQueryWrapper.eq("prerequisite_id",courseId).select("course_id");
        List<Object> objectList=coursePrerequisiteMapper.selectObjs(coursePrerequisiteQueryWrapper);
        List<Long> longList=new ArrayList<>();
        for(Object object:objectList){
            longList.add(Long.valueOf(object.toString()));
        }
        return longList;
    }

    @Override
    public Integer getOutDegree(Long courseId) {
        QueryWrapper<CoursePrerequisite> coursePrerequisiteQueryWrapper=new QueryWrapper<>();
        coursePrerequisiteQueryWrapper.eq("prerequisite_id",courseId);
        return coursePrerequisiteMapper.selectCount(coursePrerequisiteQueryWrapper);
    }
}