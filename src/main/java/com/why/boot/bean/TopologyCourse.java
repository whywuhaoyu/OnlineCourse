package com.why.boot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: TopologyCourse
 * @CreateTime: 2023/4/10 22:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopologyCourse {
    private SysCourse course;
    private List<SysCourse> prerequisites;
}