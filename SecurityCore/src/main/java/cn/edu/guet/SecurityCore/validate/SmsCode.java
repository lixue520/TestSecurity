package cn.edu.guet.SecurityCore.validate;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信验证码
 */
@Data
public class SmsCode implements Serializable {

    private static final long serialVersionUID = 7628994017701008582L;
    //验证码
    private String code;

    //保留时间
    private LocalDateTime expireTime;

    //expireIn 过期时间，单位秒
    public SmsCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }


    // 是否过期
    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
