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

public class ShiroTest01 {

    private SecurityManager securityManager;

    @Before
    public void init() {
        // 根据配置文件创建 SecurityManagerFactory。
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-test-1.ini");
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
        String username = "lisi";
        String password = "123";
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        // 主体登录。
        subject.login(token);

        // 验证用户是否登录成功。
        System.out.println(subject.isAuthenticated());
        // 获取登录数据。
        System.out.println(subject.getPrincipal());
        System.out.println(subject);

        // 登录成功后完成授权。
        // 授权：检验当前登录用户是否具有操作权限，是否具有某个角色。
        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.isPermitted("user: save"));
    }
}
