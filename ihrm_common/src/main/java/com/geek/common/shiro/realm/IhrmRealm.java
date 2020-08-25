package com.geek.common.shiro.realm;

import com.geek.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * 公共的 Realm。
 * 获取安全数据，构造权限信息。
 */
public class IhrmRealm extends AuthorizingRealm {

    public static void main(String[] args) {
        System.out.println(new Md5Hash("123456", "13800000003", 3).toString());
    }

    /**
     * 通过指定名称判断数据的存放。
     *
     * @param name
     */
    public void setName(String name) {
        super.setName("ihrmRealm");
    }

    /**
     * 授权方法。
     * 操作的时候，判断用户是否具有相应的权限。
     * 先认证 ~ 安全数据。
     * 再授权 ~ 根据安全数据获取用户具有的所有操作权限。
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取安全数据。
        ProfileResult result = (ProfileResult) principalCollection.getPrimaryPrincipal();
        // 获取权限信息。
        Set<String> apisPerms = (Set<String>) result.getRoles().get("apis");
        // 构造权限数据，返回。
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(apisPerms);
        return info;
    }
    // ---> 配置拦截器。

    /**
     * 认证方法。
     *
     * @param authenticationToken 传递的用户名密码。
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
