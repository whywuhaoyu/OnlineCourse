package com.why.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.SysCourse;
import com.why.boot.bean.SysUser;
import com.why.boot.mapper.SysUserMapper;
import com.why.boot.mapper.UserCourseCommentViewtMapper;
import com.why.boot.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysUserServiceImpl
 * @CreateTime: 2023/3/8 20:36
 */

/**
 * 使用Mybatis-Plus语句写法，简单语句可以不用在mapper.xml中配置
 */

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser getSysUserByAccount(String account) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("account",account);
        return sysUserMapper.selectOne(sysUserQueryWrapper);
    }

    @Override
    public boolean sysUserIsExistedByAccount(String account) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("account",account);
        return !StringUtils.isEmpty(sysUserMapper.selectOne(sysUserQueryWrapper));
    }

    @Override
    public boolean sysUserIsExistedByUserName(String username) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("user_name",username);
        return !StringUtils.isEmpty(sysUserMapper.selectOne(sysUserQueryWrapper));
    }

    @Override
    public boolean updatePasswordByAccount(String account,String password,String salt) {
        UpdateWrapper<SysUser> userUpdateWrapper=new UpdateWrapper<>();
        String md5Password =getMD5Password(password, salt);
        userUpdateWrapper.eq("account",account).set("password",md5Password);
        log.info("修改用户密码状态码：{}",sysUserMapper.update(null,userUpdateWrapper));
        return sysUserMapper.update(null,userUpdateWrapper) == 1;
    }

    @Override
    public void updateLastLoginTimeByAccount(String account, String lastLoginTime) {
        UpdateWrapper<SysUser> userUpdateWrapper=new UpdateWrapper<>();
        userUpdateWrapper.eq("account",account).set("last_login_time",lastLoginTime);
        sysUserMapper.update(null, userUpdateWrapper);
    }

    @Override
    public String getMD5Password(String password,String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    @Override
    public boolean updateUserNameByAccount(String account, String newUserName) {
        UpdateWrapper<SysUser> userUpdateWrapper=new UpdateWrapper<>();
        userUpdateWrapper.eq("account",account).set("user_name",newUserName);
        log.info("修改用户名状态码：{}",sysUserMapper.update(null,userUpdateWrapper));
        return sysUserMapper.update(null,userUpdateWrapper) == 1;
    }

    @Override
    public boolean updateUserById(Long userId, String account, String userName, String userMajor, String userType, boolean userEnabled) {
        UpdateWrapper<SysUser> sysUserUpdateWrapper=new UpdateWrapper<>();
        sysUserUpdateWrapper.eq("user_id",userId).set("account",account).set("user_name",userName)
                .set("user_major",userMajor).set("user_type",userType).set("user_enabled",userEnabled);
        log.info("修改用户信息状态码：{}",sysUserMapper.update(null,sysUserUpdateWrapper));
        return sysUserMapper.update(null,sysUserUpdateWrapper)==1;
    }

}