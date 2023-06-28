package com.why.boot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysUserHistory
 * @CreateTime: 2023/4/21 16:48
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserHistory {
    private Long id;
    private Long userId;
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String keyWords;
    private String createTime;

    public int getScore(String term) {
        int count = 0;
        if (keyWords != null && !keyWords.isEmpty()) {
            String[] words = keyWords.split(",");
            for (String word : words) {
                if (word.trim().equalsIgnoreCase(term)) {
                    count++;
                }
            }
        }
        return count;
    }
}