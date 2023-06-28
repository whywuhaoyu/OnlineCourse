package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.SysVideo;
import com.why.boot.mapper.SysVideoMapper;
import com.why.boot.service.SysVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysVideoServiceImpl
 * @CreateTime: 2023/3/18 20:46
 */

@Service
public class SysVideoServiceImpl extends ServiceImpl<SysVideoMapper, SysVideo> implements SysVideoService {

    @Autowired
    SysVideoMapper sysVideoMapper;

    @Override
    public String getVideoDurationByVideoId(Long videoId) {
        QueryWrapper<SysVideo> sysVideoQueryWrapper=new QueryWrapper<>();
        sysVideoQueryWrapper.eq("video_id",videoId);
        return sysVideoMapper.selectOne(sysVideoQueryWrapper).getVideoDuration();
    }
}