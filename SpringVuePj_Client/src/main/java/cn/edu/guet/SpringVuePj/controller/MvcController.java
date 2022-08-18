package cn.edu.guet.SpringVuePj.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MvcController {
    @RequestMapping("/test")
    public String test1(){
        return "test";   //返回的字符串会找到相关的html文件
    }
}
