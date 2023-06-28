package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.view.UserCourseCommentView;
import com.why.boot.mapper.UserCourseCommentViewtMapper;
import com.why.boot.service.UserCourseCommentViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: UserCourseCommentViewServiceImpl
 * @CreateTime: 2023/3/15 19:39
 */

@Slf4j
@Service
public class UserCourseCommentViewServiceImpl extends ServiceImpl<UserCourseCommentViewtMapper, UserCourseCommentView> implements UserCourseCommentViewService {

    @Autowired
    UserCourseCommentViewtMapper userCourseCommentViewtMapper;

    @Override
    public List<UserCourseCommentView> getCourseCommentByCourseId(Long courseId) {
        QueryWrapper<UserCourseCommentView> userCourseCommentViewQueryWrapper=new QueryWrapper<>();
        userCourseCommentViewQueryWrapper.eq("course_id",courseId);
        return userCourseCommentViewtMapper.selectList(userCourseCommentViewQueryWrapper);
    }
}