package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 课程视频关系实体类
 * @author: why
 * @ClassName: CourseVideoRelation
 * @CreateTime: 2023/3/20 9:25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVideoRelation implements Serializable {
    @TableId
    private Long id;
    private Long courseId;
    private Long videoId;
}