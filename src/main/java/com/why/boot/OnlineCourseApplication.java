package com.why.boot;

import com.why.boot.utils.CountUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: OnlineCourseRecommendApplication
 * @CreateTime: 2023/3/3 10:15
 */

@SpringBootApplication(scanBasePackages = "com.why")
public class OnlineCourseApplication {
    public static void main(String[] args) {
        int port = 8080;
        String portPrefix = "--server.port=";
        for (String arg : args)
        {
            if (arg.startsWith(portPrefix)) {
                port = Integer.parseInt(arg.substring(portPrefix.length()));
            }
        }
        SpringApplication.run(OnlineCourseApplication.class, args);
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}