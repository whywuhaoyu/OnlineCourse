package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: CourseQuestionRelation
 * @CreateTime: 2023/5/3 10:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseQuestionRelation implements Serializable {
    @TableId
    private Long id;
    private Long courseId;
    private Long questionId;
}