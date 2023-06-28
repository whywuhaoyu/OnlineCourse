package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.CoursePrerequisite;
import com.why.boot.bean.SysCourse;

import java.util.List;

public interface CoursePrerequisiteService extends IService<CoursePrerequisite> {

    /**
     *
     * @description:     根据课程id计算该课程的入度

     * @param courseId   课程id
     * @return: java.lang.Integer
     * @author: why
     * @time: 2023/4/4 14:35
     */
    Integer getInDegree(Long courseId);


    /**
     *
     * @description:     根据课程id，获取该课程的先修课程id集合

     * @param courseId   课程id
     * @return: java.util.List<java.lang.Long>
     * @author: why
     * @time: 2023/4/4 15:20
     */
    List<Long> getInDegreeList(Long courseId);



    /**
     *
     * @description:     根据课程id，获取该课程的先修课程集合

     * @param courseId   课程id
     * @return: java.util.List<com.why.boot.bean.SysCourse>
     * @author: why
     * @time: 2023/4/10 22:17
     */
    List<SysCourse> getInDegreeCourseList(Long courseId);



    /**
     *
     * @description:     根据课程id，获取该课程的后续课程id集合

     * @param courseId   课程id
     * @return: java.util.List<java.lang.Long>
     * @author: why
     * @time: 2023/4/4 16:54
     */
    List<Long> getOutDegreeList(Long courseId);


    /**
     *
     * @description:     根据课程id计算该课程的出度

     * @param courseId   课程id
     * @return: java.lang.Integer
     * @author: why
     * @time: 2023/4/4 14:36
     */
    Integer getOutDegree(Long courseId);

}
