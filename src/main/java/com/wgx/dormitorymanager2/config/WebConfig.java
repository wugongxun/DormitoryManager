package com.wgx.dormitorymanager2.config;

import com.wgx.dormitorymanager2.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author:wgx
 * version:1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    //登录拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login.html", "/static/**", "/login");
    }

    //放行静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //静态资源映射，映射到uploadFolder(/home/wgx/dormitoryPhoto/)，访问/home/wgx/dormitoryPhoto/**，映射到服务器上的文件夹下
        registry.addResourceHandler(uploadFolder + "**").addResourceLocations("file:" + uploadFolder);
    }
}
