package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 视频实体类
 * @author: why
 * @ClassName: SysVideo
 * @CreateTime: 2023/3/18 11:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysVideo implements Serializable {
    @TableId
    private Long videoId;
    private String videoNum;
    private String videoName;
    private String videoLocation;
    private String videoDuration;
    private Long videoUploadUser;
    private String videoUploadTime;
}