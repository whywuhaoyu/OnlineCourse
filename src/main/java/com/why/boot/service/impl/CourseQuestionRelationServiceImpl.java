package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.CourseQuestionRelation;
import com.why.boot.bean.SysQuestion;
import com.why.boot.mapper.CourseQuestionRelationMapper;
import com.why.boot.mapper.SysQuestionMapper;
import com.why.boot.service.CourseQuestionRelationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CourseQuestionRelationServiceImpl
 * @CreateTime: 2023/5/3 10:03
 */

@Service
public class CourseQuestionRelationServiceImpl extends ServiceImpl<CourseQuestionRelationMapper, CourseQuestionRelation> implements CourseQuestionRelationService {

    @Autowired
    SysQuestionMapper sysQuestionMapper;

    @Autowired
    CourseQuestionRelationMapper courseQuestionRelationMapper;

    @Override
    public List<SysQuestion> selectQuestionsByCourseId(Long courseId) {
        return getBaseMapper().selectList(new QueryWrapper<CourseQuestionRelation>()
                        .eq("course_id", courseId))
                .stream()
                .map(relation -> sysQuestionMapper.selectById(relation.getQuestionId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getQuestionIdsListByCourseId(Long courseId) {
        QueryWrapper<CourseQuestionRelation> wrapper = new QueryWrapper<>();
        wrapper.select("question_id").eq("course_id", courseId);
        List<Object> list = courseQuestionRelationMapper.selectObjs(wrapper);
        return list.stream().map(obj -> (Long) obj).collect(Collectors.toList());
    }
}