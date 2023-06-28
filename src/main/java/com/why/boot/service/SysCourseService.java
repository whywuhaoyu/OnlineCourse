package com.why.boot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.SysCourse;

import java.util.List;

public interface SysCourseService extends IService<SysCourse> {


    /**
     * @description:        根据页码以及课程一级标签返回课程分页数据
     *
     * @param pageNumber    页码
     * @param firstLabel    课程一级标签
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.why.boot.bean.SysCourse>
     * @author: why
     * @time: 2023/3/13 15:11
     */
    Page<SysCourse> getPageSysCourseByFirstLabel(Integer pageNumber, String firstLabel);


    /**
     *
     * @description: 获取课程一级标签集合

     * @return: java.util.List<java.lang.String>
     * @author: why
     * @time: 2023/3/14 11:26
     */
    List<Object> getCourseFirstLabelList();


    /**
     *
     * @description:      根据课程id获取课程信息

     * @param courseId    课程id
     * @return: com.why.boot.bean.SysCourse
     * @author: why
     * @time: 2023/3/14 21:47
     */
    SysCourse getCourseByCourseId(Long courseId);


    /**
     *
     * @description:       根据课程所属专业获取课程集合

     * @param courseMajor  课程所属专业
     * @return: java.util.List<com.why.boot.bean.SysCourse>
     * @author: why
     * @time: 2023/4/4 15:33
     */
    List<SysCourse> getSysCourseListByCourseMajor(String courseMajor);


    /**
     *
     * @description:       根据id列表获取课程列表

     * @param ids
     * @return: java.util.List<com.why.boot.bean.SysCourse>
     * @author: why
     * @time: 2023/4/22 16:16
     */
    List<SysCourse> getSysCourseByIdListByCount(List<Long> ids);




}
