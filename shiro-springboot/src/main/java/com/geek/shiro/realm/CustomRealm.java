package com.geek.shiro.realm;

import com.geek.shiro.domain.Permission;
import com.geek.shiro.domain.Role;
import com.geek.shiro.domain.User;
import com.geek.shiro.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的 realm。
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    public static void main(String[] args) {
        System.out.println(new Md5Hash("123456", "lisi", 3).toString());
    }

    /**
     * 通过指定名称判断数据的存放。
     *
     * @param name
     */
    public void setName(String name) {
        super.setName("customRealm");
    }

    /**
     * 授权方法。
     * 操作的时候，判断用户是否具有相应的权限。
     * 先认证 ~ 安全数据。
     * 再授权 ~ 根据安全数据获取用户具有的所有操作权限。
     *
     * @param principalCollection 传递的用户名和密码。
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取已认证的用户数据。
        User user = (User) principalCollection.getPrimaryPrincipal();// 得到唯一的安全数据。
        // 根据用户数据获取用户的权限信息（所有角色 + 所有权限）。
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();// 所有角色。
        Set<String> perms = new HashSet<>();// 所有权限。
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
            for (Permission perm : role.getPermissions()) {
                perms.add(perm.getCode());
            }
        }
        // 权限。
        info.setStringPermissions(perms);
        // 角色。
        info.setRoles(roles);
        return info;
    }

    /**
     * 认证方法。
     *
     * @param authenticationToken 传递的用户名密码。
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取 subject.login(upToken); 登录的用户名密码（token）。
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        // 根据用户名查询数据库。
        User user = userService.findByName(username);
        // 判断用户是否存在或者密码是否一致。
        if (user != null && user.getPassword().equals(password)) {
            // 如果一致返回安全数据。
            // 构造方法：安全数据，密码，realm 域名。
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return info;
        }
        // 不一致，返回 null（就会抛出异常）。
        return null;
    }
}
