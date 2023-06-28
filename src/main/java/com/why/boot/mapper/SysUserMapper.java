package com.why.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.boot.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
