package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 课程实体类
 * @author: why
 * @ClassName: SysCourse
 * @CreateTime: 2023/3/12 21:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysCourse implements Serializable {
    @TableId
    private Long courseId;
    private String courseName;
    private Integer viewCount;
    private String courseDescription;
    private long courseCreateUser;
    private String courseCreateTime;
    private String courseLevel;
    private String courseBase;
    private BigDecimal courseScore;
    private String imgPath;
    private String firstLabel;
    private String secondLabel;
    private String thirdLabel;
    private boolean courseState;
    private String courseMajor;
}