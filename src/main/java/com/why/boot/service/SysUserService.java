package com.why.boot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.why.boot.bean.SysCourse;
import com.why.boot.bean.SysUser;

public interface SysUserService extends IService<SysUser> {

    /**
     *
     * @description: 根据用户账号信息获取用户信息
     * @param account 用户账号
     * @return: com.why.boot.bean.SysUser
     * @author: why
     * @time: 2023/3/8 22:08
     */
    SysUser getSysUserByAccount(String account);

    /**
     * 根据用户账号判断用户是否存在
     * @param account 用户账号
     * @return boolean
     */
    boolean sysUserIsExistedByAccount(String account);

    /**
     *
     * @description: 根据用户名判断用户是否存在
     * @param username 用户名
     * @return: boolean
     * @author: why
     * @time: 2023/3/11 11:59
     */
    boolean sysUserIsExistedByUserName(String username);


    /**
     *
     * @description: 根据用户账号更改用户密码
     * @param account 用户账号
     * @return: boolean
     * @author: why
     * @time: 2023/3/11 10:30
     */
    boolean updatePasswordByAccount(String account,String password,String salt);



    /**
     *
     * @description: 根据用户账号修改用户最后登录时间
     * @param account 用户账号
     * @param lastLoginTime 最后登录时间
     * @author: why
     * @time: 2023/3/12 21:58
     */
    void updateLastLoginTimeByAccount(String account, String lastLoginTime);


    /**
     *
     * @description: MD5加密算法，保障数据安全性
     * @param password 用户密码
     * @param salt 盐值
     * @return: java.lang.String
     * @author: why
     * @time: 2023/3/25 22:28
     */
     String getMD5Password(String password, String salt);


    /**
     *
     * @description: 根据用户账号修改用户名
     * @param account 用户账号
     * @param newUserName 新用户名
     * @return: boolean
     * @author: why
     * @time: 2023/3/26 10:58
     */
    boolean updateUserNameByAccount(String account,String newUserName);


    /**
     *
     * @description:         根据用户id修改用户信息

     * @param userId         用户id
     * @param account        用户账号
     * @param userName       用户名
     * @param userMajor      用户专业
     * @param userType       用户类型
     * @param userEnabled    是否可用
     * @return: boolean
     * @author: why
     * @time: 2023/4/30 11:16
     */
    boolean updateUserById(Long userId,String account,String userName,String userMajor,String userType,boolean userEnabled);
}
