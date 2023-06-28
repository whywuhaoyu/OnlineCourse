package com.why.boot.config;

import com.why.boot.interceptor.LoginInterceptor;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: AdminWebConfig
 * @CreateTime: 2023/3/4 16:45
 */

/**
 * 1、编写一个拦截器实现HandlerInterceptor接口
 * 2、拦截器注册到容器中（实现WebMvcConfig接口）
 * 3、指定拦截规则，注意放行静态资源
 *
 * @EnableWebMvc:全面接管
 *    1、静态资源、视图解析器、欢迎页......全部失效
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")    //所有请求都被拦截，包括静态资源请求
                .excludePathPatterns("/","/login","/toLogin","/register","/toRegister","/updatePassword","/toUpdatePassword","/logout",
                        "/css/**", "/fonts/**","/iconfont/**", "/images/**","/js/**");
    }


}