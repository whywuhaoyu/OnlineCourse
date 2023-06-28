package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.CourseQuestionRelation;
import com.why.boot.bean.SysQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseQuestionRelationService extends IService<CourseQuestionRelation> {

    /*根据课程id或取题目集合*/
    List<SysQuestion> selectQuestionsByCourseId(@Param("courseId") Long courseId);

    List<Long> getQuestionIdsListByCourseId(@Param("courseId") Long courseId);
}
