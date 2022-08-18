package cn.edu.guet.SpringVuePj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * formLogin().successHandler() 中需要的处理器类型
 */
@Component("myAuthenticationSuccessHandler")
//MyAuthenticationSuccessHandler:我的身份验证成功处理
//MyAuthenticationSuccessHandler：已保存请求感知身份验证成功处理程序
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    // spring 是使用jackson来进行处理返回数据的
    // 所以这里可以得到他的实例
    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    private final static String LoginType = "JSON";

    /**
     * @param authentication 封装了所有的认证信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (LoginType == "JSON") {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}