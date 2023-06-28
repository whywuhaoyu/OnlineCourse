package com.why.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.boot.bean.SysQuestion;
import com.why.boot.mapper.SysQuestionMapper;
import com.why.boot.service.SysQuestionService;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysQuestionServiceImpl
 * @CreateTime: 2023/5/2 15:04
 */

@Service
public class SysQuestionServiceImpl extends ServiceImpl<SysQuestionMapper, SysQuestion> implements SysQuestionService {
}