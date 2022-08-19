package cn.edu.guet.SecurityCore.authentication;


import cn.edu.guet.SecurityCore.pojo.RestResult;
import cn.edu.guet.SecurityCore.properties.LoginResponseType;
import cn.edu.guet.SecurityCore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 认证失败处理接口
 */
@Component("myAuthenticationFailureHandler")
public class ShiyanlouAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if(LoginResponseType.JSON.equals(securityProperties.getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(RestResult.build("619", "认证失败")));

        }else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
