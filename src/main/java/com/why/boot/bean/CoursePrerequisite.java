package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 课程先修关系实体类
 * @author: why
 * @ClassName: CoursePrerequisite
 * @CreateTime: 2023/4/4 11:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CoursePrerequisite {

    @TableId
    private Long id;
    private Long prerequisite_id;   //先修课程id
    private Long courseId;          //课程id
}