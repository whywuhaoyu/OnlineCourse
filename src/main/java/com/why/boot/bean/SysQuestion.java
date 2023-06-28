package com.why.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysQuestion
 * @CreateTime: 2023/5/2 14:54
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysQuestion implements Serializable {
    @TableId
    private Long  questionId;
    private String questionDescription;
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String questionType;
    private String contentType;
    private String questionAnswer;
    private Integer questionAward;


}