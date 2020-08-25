package com.geek.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 realm 对象。
 */
public class PermissionRealm extends AuthorizingRealm {

    /**
     * 自定义 realm 名称。
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("permissionRealm");
    }

    // authorize
    //  v. 批准;授权
    // authorization
    //  n. 批准；授权；批准书；授权书

    /**
     * 授权。（用户的授权数据）。
     * 主要目的：根据认证数据获取到用户的权限信息。
     *
     * @param principalCollection 包含了所以已认证的安全数据。
     * @return 授权数据。
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权。。。");
        // 获取安全数据 username，用户 id。
        String username = (String) principalCollection.getPrimaryPrincipal();

        // 根据 id 或名称查询用户。
        // 查询用户的角色和权限信息。
        List<String> perms = new ArrayList<>();
        perms.add("user:save");
        perms.add("user:update");
        List<String> roles = new ArrayList<>();
        roles.add("role1");
        roles.add("role2");

        // 构造返回。
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 设置权限集合。
        info.addStringPermissions(perms);
        // 设置角色集合。
        info.addRoles(roles);
        return info;
    }

    // authenticate
    //  v. 证明…是真实的；证实
    // authentication
    //  n. 身份验证；认证；鉴定

    /**
     * 认证。（根据用户名密码登录）。
     * 主要目的：将安全数据存入到 shiro 进行博阿管。
     *
     * @param authenticationToken 登录构造的 UsernamePasswordToken。
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证。。。");
        // 构造 UsernamePasswordToken。
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        // 获取输入的用户名密码。
        String username = usernamePasswordToken.getUsername();
        char[] chars = usernamePasswordToken.getPassword();
        String password = new String(chars);
        // 根据用户名查询数据库。

        // 比较密码和数据库中的密码是否一致。（密码可能需要加密）。
        if ("123".equals(password)) {
            // 如果成功，向 shiro 存入安全数据。
            // - 安全数据。
            // - 密码。
            // - 当前 realm 域名称。
            return new SimpleAuthenticationInfo(username, password, getName());
        } else {
            // 失败。抛出异常或返回 null。
            throw new RuntimeException("用户名或密码错误。");
        }
    }
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return super.supports(token);
//    }
//
//    @Override
//    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
//        return super.getAuthorizationInfo(principals);
//    }
}

// 认证流程。

// 首先调用 subject.login(token); 进行登录，其会自动委托给 Security Manager，调用之前必须通过 SecurityUtils.setSecurityManager(); 设置。
// SecurityManager 负责真正的身份验证逻辑。ta 会委托给 Authenticator 进行身份验证。
// Authenticator 才是真正的身份验证者，Shiro API 中核心的身份认证入口点，此处可以自定义插入自己的实现。
// Authenticator 可能会委托给相应的 AuthenticationStrategy 进行多 Realm 身份验证， 默认 ModularRealmAuthenticator 会调用 AuthenticationStrategy 进行多 Realm 身份验证。
// Authenticator 会把相应的 token 传入 Realm，从 Realm 获取身份验证信息，如果没有返回/ 抛出异常表示身份验证失败了。此处可以配置多个 Realm，将按照相应的顺序及策略进行访问。


// 授权流程。

// 首先调用 Subject.isPermitted/hasRole 接口，其会委托给 SecurityManager，而 SecurityManager 接着会委托给 Authorizer。
// Authorizer 是真正的授权者，如果我们调用如 isPermitted("user:view");，其首先会通过 PermissionResolver 吧字符串转换成相应的 Permission 实例。
// 在进行授权之前，其会调用相应的 Realm 获取 subject 相应的角色 / 权限用于匹配传入的角色 / 权限。
// Authorizer 会判断 Realm 的角色 ／ 权限是否和传入的匹配，如果有多个 Realm ，会委托给 ModularRealmAuthorizer 进行循环判，如果匹配如 isPermitted/hasRole 会返回 true，否则返回 false 表示授权失败。
