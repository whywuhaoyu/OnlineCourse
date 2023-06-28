package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 用户选课实体类
 * @author: why
 * @ClassName: learnerCourseStudy
 * @CreateTime: 2023/3/16 22:51
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLearnerStudy implements Serializable {
    @TableId
    private Long id;
    private Long courseId;
    private Long userId;
    private String courseChooseTime;
    private String courseProgress;
    private Long videoId;
    private String videoName;   //上次观看视频
    private String lastWatchTime;  //上次观看时间
    private Integer grade;         //试题测试成绩
}