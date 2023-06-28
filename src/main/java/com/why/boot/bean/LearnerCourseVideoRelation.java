package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 用户课程视频关系实体类
 * @author: why
 *
 * @ClassName: LearnerCourseVideoRelation
 * @CreateTime: 2023/3/31 16:27
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearnerCourseVideoRelation implements Serializable {

    @TableId
    private Long id;
    private Long userId;
    private Long courseId;
    private Long videoId;
    private String videoDuration;     //视频时长
    private String videoCurrent;      //视频当前播放位置
    private boolean isDone;           //视频是否已经播放完成
}