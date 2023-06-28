package com.why.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.boot.bean.view.UserCourseStudyView;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: UserCourseStudyViewMapper
 * @CreateTime: 2023/3/16 21:19
 */

@Mapper
public interface UserCourseStudyViewMapper extends BaseMapper<UserCourseStudyView> {
}