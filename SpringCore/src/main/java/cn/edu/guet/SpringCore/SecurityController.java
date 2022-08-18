package cn.edu.guet.SpringCore;


import cn.edu.guet.SpringComment.pojo.RestResult;
import cn.edu.guet.SpringCore.properties.SecurityConstant;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SecurityController {

    // 封装了引发跳转请求的工具类，看实现类应该是从session中获取的
    private RequestCache requestCache = new HttpSessionRequestCache();

    // spring的工具类：封装了所有跳转行为策略类
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 当需要身份认证时跳转到这里
     */
    @GetMapping(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL)
    public RestResult requirAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // 如果有引发认证的请求
        // spring 在跳转前应该会把信息存放在某个地方？
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println(("引发跳转的请求：" + targetUrl));
            // 如果是html请求，则跳转到登录页
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, "/login");
            }
        }
        // 否则都返回需要认证的json串
        return new RestResult("访问受限，请前往登录页面");
    }
}