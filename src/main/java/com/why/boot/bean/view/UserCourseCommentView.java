package com.why.boot.bean.view;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 用户课程评论视图实体类
 * @author: why
 * @ClassName: UserCourseCommentView
 * @CreateTime: 2023/3/15 19:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserCourseCommentView implements Serializable {
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseName;
    private String commentTime;
    private String commentContent;
    private BigDecimal courseScore;
    private String headImgPath;
    private String imgPath;
}