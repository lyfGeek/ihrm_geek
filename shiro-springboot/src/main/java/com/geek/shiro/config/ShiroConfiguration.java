package com.geek.shiro.config;

import com.geek.shiro.realm.CustomRealm;
import com.geek.shiro.session.CustomSessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置 Shiro。
 */
@Configuration
public class ShiroConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    // 配置 shiro 的过滤器工厂。

    // 创建 realm。
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    /**
     * 创建安全管理器。
     *
     * @param customRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(customRealm);

        // 将自定义的会话管理器注册到安全管理器中。
        securityManager.setSessionManager(sessionManager());
        // 将自定义的 redis 缓存管理器注册到安全管理器中。
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    /**
     * 在 web 程序中，shiro 进行权限控制全部是通过一组过滤器集合进行控制。
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 创建过滤器工厂。
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        // 设置安全管理器。
        filterFactory.setSecurityManager(securityManager);
        // 通用配置（跳转登录页面 or 未授权跳转的页面）。
        filterFactory.setLoginUrl("/autherror?code=1");// 未登录跳转的 url。
        filterFactory.setUnauthorizedUrl("/autherror?code=2");// 未授权跳转的 url。

        // 设置过滤器集合。
        /*
        设置所有的过滤器：有顺序 map ~ LinkedHashMap。
            key = 拦截的 url 地址。
            value = 过滤器类型。
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/user/home", "anon");// 当前请求地址可以匿名访问。

        // 使用过滤器的形式配置请求地址的依赖权限。
        // 具有某种权限才能访问。
//        filterMap.put("/user/home", "perms[user-home]");// 不具备指定的权限，跳转到 setUnauthorizedUrl 地址。

        // 使用过滤器的形式配置请求地址的依赖角色。
//        filterMap.put("/user/home", "roles[系统管理员]");

        filterMap.put("/user/**", "authc");// 当前请求地址必须认证之后可以访问。

        filterFactory.setFilterChainDefinitionMap(filterMap);

        return filterFactory;
    }

    // ~ ~ ~ ~ ~

    /**
     * redis 控制器，操作 redis。
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        return redisManager;
    }

    /**
     * sessionDao。
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    /**
     * 会话管理器。
     */
    public DefaultWebSessionManager sessionManager() {
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * 缓存管理器。
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    // ~ ~ ~ ~ ~

    /**
     * 开启对 shiro 注解的支持。
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
