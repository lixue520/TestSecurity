package cn.edu.guet.SecurityCore.validate;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class ValidateCodeController {


    static final String SESSION_KEY = "SESSION_KEY_SMS_CODE";
    // 这里又使用了spring的工具类来操作session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/sms")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String mobile = request.getParameter("mobile");
        System.out.println("向手机号" + mobile + "发送验证码");

        String code = RandomStringUtils.randomNumeric(6);
        SmsCode smsCode = new SmsCode(code, 60 * 10);

        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);

        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(smsCode.getCode());


    }
}