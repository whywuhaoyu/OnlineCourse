package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.SysCourse;
import com.why.boot.mapper.SysCourseMapper;
import com.why.boot.service.SysCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysCourseServiceImpl
 * @CreateTime: 2023/3/13 15:21
 */
@Slf4j
@Service
public class SysCourseServiceImpl extends ServiceImpl<SysCourseMapper, SysCourse> implements SysCourseService {

    @Autowired
    SysCourseMapper sysCourseMapper;

    @Override
    public Page<SysCourse> getPageSysCourseByFirstLabel(Integer pageNumber, String firstLabel) {
        /*每页显示四条数据*/
        Page<SysCourse> sysCoursePage=new Page<>(pageNumber,4);
        QueryWrapper<SysCourse> sysCourseQueryWrapper=new QueryWrapper<>();
        sysCourseQueryWrapper.eq("first_label",firstLabel);
        return page(sysCoursePage,sysCourseQueryWrapper);
    }

    @Override
    public List<Object> getCourseFirstLabelList() {
        QueryWrapper<SysCourse> sysCourseQueryWrapper=new QueryWrapper<>();
        sysCourseQueryWrapper.select("distinct first_label");
        sysCourseMapper.selectObjs(sysCourseQueryWrapper);
        return sysCourseMapper.selectObjs(sysCourseQueryWrapper);
    }

    @Override
    public SysCourse getCourseByCourseId(Long courseId) {
        QueryWrapper<SysCourse> sysCourseQueryWrapper=new QueryWrapper<>();
        sysCourseQueryWrapper.eq("course_id",courseId);
        return sysCourseMapper.selectOne(sysCourseQueryWrapper);
    }

    @Override
    public List<SysCourse> getSysCourseListByCourseMajor(String courseMajor) {
        QueryWrapper<SysCourse> sysCourseQueryWrapper=new QueryWrapper<>();
        sysCourseQueryWrapper.eq("course_major",courseMajor);
        return sysCourseMapper.selectList(sysCourseQueryWrapper);
    }

    @Override
    public List<SysCourse> getSysCourseByIdListByCount(List<Long> ids) {
        List<SysCourse> sysCourseList=new ArrayList<>();
        for(Long courseId:ids){
            QueryWrapper<SysCourse> sysCourseQueryWrapper=new QueryWrapper<>();
            sysCourseQueryWrapper.eq("course_id",courseId);
            sysCourseList.add(sysCourseMapper.selectOne(sysCourseQueryWrapper));
        }
        return sysCourseList;
    }

}