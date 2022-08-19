package cn.edu.guet.SecurityCore.seurity;

//import com.shiyanlou.core.authentication.ShiyanlouAuthenticationFailureHandler;
//import com.shiyanlou.core.authentication.ShiyanlouAuthenticationSuccessHandler;
//import com.shiyanlou.core.properties.SecurityProperties;
//import com.shiyanlou.core.validate.ValidateCodeFilter;
import cn.edu.guet.SecurityCore.authentication.ShiyanlouAuthenticationFailureHandler;
import cn.edu.guet.SecurityCore.authentication.ShiyanlouAuthenticationSuccessHandler;
import cn.edu.guet.SecurityCore.properties.SecurityProperties;
import cn.edu.guet.SecurityCore.validate.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * spring security 安全配置项
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ShiyanlouAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private ShiyanlouAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setFailureHandler(authenticationFailureHandler);
        // 由源码得知，在最前面的是UsernamePasswordAuthenticationFilter
        http
                .authorizeRequests()
                // 放过 /login 表单认证相关
                .antMatchers( "/authentication/*","/code/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(securityProperties.getAuthenticationUrl())
                .loginProcessingUrl(securityProperties.getLoginProcessingUrl())
                // 加入自定义处理器
                .successHandler(authenticationSuccessHandler)
                .permitAll()
                .and()
                // csrf 防护关掉
                .csrf().disable()
                .logout()
                .permitAll();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}