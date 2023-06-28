package com.why.boot.bean.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @Description: 用户学习课程视图实体类
 * @author: why
 * @ClassName: UserCourseStudyView
 * @CreateTime: 2023/3/16 21:04
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseStudyView implements Serializable {
    private Long userId;
    private Long courseId;
    private String courseName;
    private String imgPath;
    private String courseChooseTime;
    private String courseProgress;
    private String lastWatchTime;
    private Long videoId;
    private String videoName;


}