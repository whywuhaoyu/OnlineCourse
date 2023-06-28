package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 用户课程评论实体类
 * @author: why
 * @ClassName: CourseLearnerComment
 * @CreateTime: 2023/3/17 16:10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLearnerComment implements Serializable {
    @TableId
    private Long id;
    private Long courseId;
    private Long userId;
    private String commentTime;
    private String commentContent;
    private BigDecimal courseScore;
}