package cn.edu.guet.SpringVuePj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 自定义数据源来获取数据
 这里只要是存在一个自定义的 UserDetailsService，那么 security 将会使用该实例进行配置
 */
@Component
/*
* @controller 控制器（注入服务）；用于标注控制层；调用服务层接口进行注入
* @service 服务（注入dao);主要用来进行业务的逻辑处理
* @repository 储存库（实现dao的访问）用于标注数据访问层；也可以说用于标注数据访问组件，即DAO组件
* @Component (将普通的pojo（实体类）实例化到spring容器，相当于<bean id="" class=""/>)
* 泛指各种组件，也就是说我们的类不属于以上归类时就可以使用Component来标注这个类
* 下面写这个是引入component的扫描组件 （这是在配置文件中的书写格式,如spring mvc中的
* applicationcontent.xml，在spring boot中的话，
* 因采用的是零配置所以要直接在类上加入@component注解就可以了）
<context:component-scan base-package=”com.mmnc”>
上面的这个例子是引入Component组件的例子，其中base-package表示为需要扫描的所有子包。
共同点：被@controller 、@service、@repository 、@component 注解的类，都会把这些类纳入进spring容器中进行管理
* */
public class MyUserDetailsService implements UserDetailsService {
//1.要进行用户认证，需要先获取到用户的验证信息；
//2.SpringSecurity提供了UserDetailsService 接口来获取用户信息
//3.该接口用于加载用户特定的数据，它在整个框架中作为用户DAO使用。
//该接口只需要一个只读方法
    // 可以从任何地方获取数据

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Get user information:" + username);

        // 写死一个密码，赋予一个 admin 权限【正常应该是在数据库中查询出来】
        //commaSeparatedStringToAuthorityList:逗号分隔符权限列表
        //这里可以拿到用户的数据
        //Spring Security 5+ 后密码策略变更了。必须使用 PasswordEncoder 方式，
        //也就是你存储密码的时候需要使用 {noop}123456
        // 这样的方式。这个在官网文档中有讲到，花括号里面的是 encoder id，这个支持的全部列表在以下的方法中定义：
        //以下为非加密安全策略下的写死密码与自由用户
//        return new User(username, "{noop}123456",
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        String password=passwordEncoder.encode("123456");
        return new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
//    框架会把提交的密码使用我们定义的 passwordEncode
//    加密后调用 org.springframework.security.crypto.password.PasswordEncoder#matches 方法，
//    与返回的 User 中的密码进行比对，配对正常就验证通过。效果和上面一样。
}