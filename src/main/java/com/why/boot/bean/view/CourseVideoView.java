package com.why.boot.bean.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 课程视频视图实体类
 * @author: why
 * @ClassName: CourseVideoView
 * @CreateTime: 2023/3/18 16:04
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVideoView implements Serializable {
    private Long courseId;
    private Long videoId;
    private String courseName;
    private String videoName;
    private String videoLocation;
    private String videoDuration;
    private String videoUploadTime;
}