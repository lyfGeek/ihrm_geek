package com.geek.system.shiro.realm;

import com.geek.common.shiro.realm.IhrmRealm;
import com.geek.domain.system.Permission;
import com.geek.domain.system.User;
import com.geek.domain.system.response.ProfileResult;
import com.geek.system.service.IPermissionService;
import com.geek.system.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRealm extends IhrmRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionService permissionService;

    // 授权方法。
    // 在 com.geek.common.shiro.realm.IhrmRealm 中。
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 认证方法。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户手机号密码。
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String mobile = upToken.getUsername();
        String password = new String(upToken.getPassword());
        // 根据手机号查询用户。
        User user = userService.findByMobile(mobile);
        // 判断用户是否存在，用户密码是否和输入密码一致。
        if (user != null && user.getPassword().equals(password)) {
            // 构造安全数据（用户基本信息，权限信息 ProfileResult）。
            ProfileResult profileResult = null;

            if ("user".equals(user.getLevel())) {
                profileResult = new ProfileResult(user);
            } else {
                Map map = new HashMap();
                if ("coAdmin".equals(user.getLevel())) {
                    map.put("enVisible", "1");
                }
                List<Permission> list = permissionService.findAll(map);
                profileResult = new ProfileResult(user, list);
            }

            // 如果一致返回安全数据。
            // 构造方法：安全数据，密码，realm 域名。
            return new SimpleAuthenticationInfo(profileResult, user.getPassword(), this.getName());
        }
        // 不一致，返回 null（就会抛出异常）。
        return null;
    }
}
