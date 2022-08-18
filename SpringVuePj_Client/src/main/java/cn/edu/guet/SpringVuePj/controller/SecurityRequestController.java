package cn.edu.guet.SpringVuePj.controller;


import cn.edu.guet.SpringVuePj.bean.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SecurityRequestController {
    // 封装了引发跳转请求的工具类，看实现类应该是从 session 中获取的
    //session机制则是又一种在客户端与服务器之间保持状态的解决方案。
    private RequestCache requestCache = new HttpSessionRequestCache();

    // spring 的工具类：封装了所有跳转行为策略类
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 当需要身份认证时跳转到这里
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)//@ResponseStatus的作用就是为了改变HTTP响应的状态码，
    //当tomcat接收到客户端发送请求后，tomcat将http请求的信息封装成HttpServletRequest对象，
    //然后经过service()方法分发给doGet()方法和doPost()方法取调用。通过HttpServletRequest对象
    //获取想要的请求信息
    public RestResult requirAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // 如果有引发认证的请求
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl(); //获取重定向网址
            System.out.println(("引发跳转的请求：" + targetUrl));
            // 如果是 html 请求，则跳转到登录页
            //忽略大小写结尾 endsWithIgnoreCase
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                //实现转跳
                redirectStrategy.sendRedirect(request, response, "/login");
            }
        }
        // 否则都返回需要认证的 json 串
        return new RestResult("访问受限，请前往登录页面"); //自封装的响应体
    }
}

