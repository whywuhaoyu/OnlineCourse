package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.SysUserHistory;
import com.why.boot.mapper.SysUserHistoryMapper;
import com.why.boot.service.SysUserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysUserHistorySerServiceeImpl
 * @CreateTime: 2023/4/21 16:56
 */

@Service
public class SysUserHistoryServiceImpl extends ServiceImpl<SysUserHistoryMapper, SysUserHistory> implements SysUserHistoryService {

    @Autowired
    SysUserHistoryMapper sysUserHistoryMapper;

    @Override
    public List<SysUserHistory> getUserHistoryByUserId(Long userId) {
        QueryWrapper<SysUserHistory> sysUserHistoryQueryWrapper = new QueryWrapper<>();
        sysUserHistoryQueryWrapper.eq("user_id",userId);
        return sysUserHistoryMapper.selectList(sysUserHistoryQueryWrapper);
    }
}