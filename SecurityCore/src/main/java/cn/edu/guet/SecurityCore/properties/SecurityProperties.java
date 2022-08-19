package cn.edu.guet.SecurityCore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Security 配置文件加载
 */
@Component
@ConfigurationProperties(prefix = "shiyanlou.security")
@Data
public class SecurityProperties {

    //当请求需要身份认证时，默认跳转的url
    private String authenticationUrl = SecurityConstant.DEFAULT_UNAUTHENTICATION_URL;
    //默认的用户请求登录处理url
    private String loginProcessingUrl = SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_FORM;

    //默认的登录响应方式
    private LoginResponseType loginType = LoginResponseType.JSON;

}
