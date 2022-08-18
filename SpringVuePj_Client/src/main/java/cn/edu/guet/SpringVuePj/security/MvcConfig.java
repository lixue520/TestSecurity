package cn.edu.guet.SpringVuePj.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//1.使用配置视图控制器来暴露模板中的页面（配置文件方式实现转跳）
//2.当然也可以使用控制器方式进行转跳
// Spring MVC配置类
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //添加视图控制器  ==（查看视图控制注册表s  注册表 ）
    public void addViewControllers(ViewControllerRegistry registry) {
        //注册表.添加视图控制
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello.html").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
}