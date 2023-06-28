package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.SysCarousel;

import java.util.List;

public interface SysCarouselService extends IService<SysCarousel> {

    /**
     *
     * @description: 随机获取固定数量的轮播图
     * @param num 轮播图数量
     * @return: java.util.List<com.why.boot.bean.SysCarousel>
     * @author: why
     * @time: 2023/3/25 14:13
     */
    List<SysCarousel> getSysCarouselRandom(int num);
}
