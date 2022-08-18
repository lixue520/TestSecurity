package cn.edu.guet.SpringCore.properties;

/**
 * 安全业务常量类
 */
public interface SecurityConstant {

    //当请求需要身份认证时，默认跳转的url
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    //默认的用户名密码登录请求处理url
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

}