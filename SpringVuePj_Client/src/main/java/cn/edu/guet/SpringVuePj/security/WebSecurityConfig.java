package cn.edu.guet.SpringVuePj.security;

import cn.edu.guet.SpringVuePj.service.MyAuthenticationSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;   //用于密码的加解密
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @version 1.0
 * @Author qin
 * @Date 2022/8/17 23:39
 */

@Configuration  //配置声明
@EnableWebSecurity //启动Spring Security的Web安全支持，并提供Spring MVC继承
//扩展了WebSecurityConfigurerAdapter,覆盖了一些方法来设置Web安全配置规则的细节
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

   //定义了哪些URL路径应该被保护，“/”和"/home"路径被配置为不需要任何身份验证
   /*
   *初始界面/登录界面/登出界面均被授权开放
   * HttpSecurity: http 安全对象
   * authorizeRequests：授权请求
   * antMatchers：配置路径
   * permitAll：全部被允许
   * authenticated:执行认证
   * formLogin():表单登录
   * LoginPage("/login")：表单登录页面
   * logout()：登出时也允许转跳
   * 用户成功登陆时他们将被重定向到先前请求的需要身份认证的页面。LoginPage指定
   * 自定义登录页面，人人可看
   * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //将 /login表单认证解放
                .antMatchers("/authentication/*","/","/home","/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
//                更换成自定义的一个真实存在的处理器地址；便于控制器判断是需要转跳还是返回JSON
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                //加入自定义处理器
                .successHandler(myAuthenticationSuccessHandler)
                .permitAll()
                .and()
                .csrf().disable()  // csrf 防护关掉
                .logout()
                .permitAll();
    }



    //该方法，它将单个用户设置在内存中。该用户名为user;密码为123456;角色为USER
    /*
    * 配置全局
    * AuthenticationManagerBuilder：身份验证 管理生成器
    * auth:授权
    * inMemoryAuthentication：在内存认证
    * passwordEncoder：密码编码器
    * passwordEncoder：BCrypt密码编码器
    * */

//    因为定义了用户的接口：因此不需要内存当中的登录用户密码了
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("USER");
//    }

    //采用内置的密码加密方式；必要时可以加盐重写；加Bean交给本容器管理
    //自动加密密码
    //配置该bean后，声明为容器的组件可以扫描到该Bean;进行自动连接装配
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //返回的加密密码与MyUser接口返回的User密码进行比对
    }


}
