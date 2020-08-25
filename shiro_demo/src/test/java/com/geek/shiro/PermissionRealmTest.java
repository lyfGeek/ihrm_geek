package com.geek.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

public class PermissionRealmTest {

    private SecurityManager securityManager;

    @Before
    public void init() {
        // 根据配置文件创建 SecurityManagerFactory。
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-test-2.ini");
        // 通过工厂获取 SecurityManager。
        securityManager = factory.getInstance();
        // 将 SecurityManager 绑定到当前运行环境。
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testLogin() {
        // 从当前运行环境构造 Subject。
        Subject subject = SecurityUtils.getSubject();
        // 构造 shiro 登录的数据。
        String username = "zhangsan";
        String password = "123";
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        // 主体登录。
        // 执行 login --> 自动找 realm 域中的认证方法。
        subject.login(token);
        // 执行认证。。。
        //true

        // 验证用户是否登录成功。
        System.out.println(subject.isAuthenticated());

        // 登录成功后完成授权。
        // 授权：检验当前登录用户是否具有操作权限，是否具有某个角色。
        // 授权 --> 自动找 realm 域中的授权方法。
        System.out.println(subject.hasRole("role1"));
        // 执行授权。。。
        //true

        // 授权 --> 自动找 realm 域中的授权方法。
        System.out.println(subject.isPermitted("user:save"));
        // 执行授权。。。
        //true
    }

}
