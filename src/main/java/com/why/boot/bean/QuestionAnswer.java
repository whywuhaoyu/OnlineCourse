package com.why.boot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description: TODO
 * @author: why
 * @ClassName: QuestionAnswer
 * @CreateTime: 2023/5/3 20:03
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswer{
    private Long questionId;
    private String[] userAnswer;
}