package cn.edu.guet.SpringCore.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证失败异常，继承身份验证异常的基类
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }

}