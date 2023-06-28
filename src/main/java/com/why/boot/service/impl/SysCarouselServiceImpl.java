package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.SysCarousel;
import com.why.boot.mapper.SysCarouselMapper;
import com.why.boot.service.SysCarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysCarouselServiceImpl
 * @CreateTime: 2023/3/25 14:09
 */

@Service
public class SysCarouselServiceImpl extends ServiceImpl<SysCarouselMapper, SysCarousel> implements SysCarouselService {

    @Autowired
    SysCarouselMapper sysCarouselMapper;

    @Override
    public List<SysCarousel> getSysCarouselRandom(int num) {
        int count=this.count();
        int random=(int)(Math.random()*count);
        if(random>count-num){
            random=count-num;
        }
        QueryWrapper<SysCarousel> sysCarouselQueryWrapper=new QueryWrapper<>();
        sysCarouselQueryWrapper.orderByAsc("id");
        sysCarouselQueryWrapper.last("limit "+ random +","+num);
        return sysCarouselMapper.selectList(sysCarouselQueryWrapper);
    }
}