package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 用户实体类
 * @author: why
 * @ClassName: User
 * @CreateTime: 2023/3/8 16:28
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
    @TableId
    private Long userId;
    private String account;
    private String userName;
    private String password;
    private String userType;
    private String userMajor;
    private String userInterest;
    private String lastLoginTime;
    private boolean userEnabled;
    private String createTime;
    private String headImgPath;
    private String salt;
}