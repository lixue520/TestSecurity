package cn.edu.guet.SpringVuePj.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 如果没有使用RestController，那么SpringBoot和SpringMVC的方法，如果返回的是String类型
 * 说明方法执行完毕后，要进行页面跳转
 * RestController：说明SpringBoot的方法，将来要返回JSON格式，就不再进行《页面跳转》
 */
@RestController   //RestController与Controller的区别就在于返回的String是否会是文件.html
public class LoginController {

    @PostMapping("loginu")
    // SpringBoot还提供了接收JSON格式的参数
    public String login(String username,String password){// 表单格式
    //public String login(@RequestBody LoginBean loginBean) {// JSON格式
        System.out.println("准备登录");
        System.out.println(username);
        System.out.println(password);
        return "";
    }
}
