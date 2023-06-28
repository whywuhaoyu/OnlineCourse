package com.why.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.SysUserHistory;

import java.util.List;

public interface SysUserHistoryService extends IService<SysUserHistory> {

    /**
     *
     * @description:   根据用户id获取用户历史记录

     * @param userId   用户id
     * @return: java.util.List<com.why.boot.bean.SysUserHistory>
     * @author: why
     * @time: 2023/4/22 10:58
     */
    List<SysUserHistory> getUserHistoryByUserId(Long userId);
}
