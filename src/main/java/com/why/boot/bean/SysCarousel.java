package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 首页轮播图实体类
 * @author: why
 * @ClassName: SysCarousel
 * @CreateTime: 2023/3/25 14:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SysCarousel implements Serializable {
    @TableId
    private int id;
    private String carouselImgPath;
    private Long uploadUser;
    private String uploadTime;
}