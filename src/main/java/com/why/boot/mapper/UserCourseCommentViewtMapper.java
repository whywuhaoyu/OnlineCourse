package com.why.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.boot.bean.view.UserCourseCommentView;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: UserCourseCommentMapper
 * @CreateTime: 2023/3/15 19:41
 */

@Mapper
public interface UserCourseCommentViewtMapper extends BaseMapper<UserCourseCommentView> {
}