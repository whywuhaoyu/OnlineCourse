package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.view.UserCourseStudyView;
import com.why.boot.mapper.UserCourseStudyViewMapper;
import com.why.boot.service.UserCourseStudyViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: UserCourseStudyViewServiceImpl
 * @CreateTime: 2023/3/16 21:24
 */
@Service
public class UserCourseStudyViewServiceImpl extends ServiceImpl<UserCourseStudyViewMapper, UserCourseStudyView> implements UserCourseStudyViewService{

    @Autowired
    UserCourseStudyViewMapper userCourseStudyViewMapper;

    @Override
    public boolean isExistedCollectCourse(Long courseId, Long userId) {
        QueryWrapper<UserCourseStudyView> userCourseStudyViewQueryWrapper = new QueryWrapper<>();
        userCourseStudyViewQueryWrapper.eq("course_id",courseId).eq("user_id",userId);
        return !StringUtils.isEmpty(userCourseStudyViewMapper.selectOne(userCourseStudyViewQueryWrapper));
    }
}