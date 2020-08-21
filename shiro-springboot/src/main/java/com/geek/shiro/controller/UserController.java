package com.geek.shiro.controller;

import com.geek.shiro.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    // 个人主页。
    // 使用 shiro 注解鉴权。
    // @RequiresPermissions() ~ 访问此方法必须具备的权限。
    // @RequiresRoles() ~ 访问此方法必须具备的角色。

    /**
     * 过滤器：如果权限信息不匹配 setUnauthorizedUrl 地址。
     * 注解：如果权限信息不匹配，抛出异常。
     */
//    @RequiresRoles()// 访问此方法必须具备的角色。
    @RequiresPermissions("user-home")// 访问此方法必须具备的权限。
    @RequestMapping(value = "/user/home")
    public String home() {
        return "访问个人主页成功。";
    }

    // 添加。
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String add() {
        return "添加用户成功。";
    }

    // 删除。
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete() {
        return "删除用户成功。";
    }

    // 更新。
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String update(@PathVariable String id) {
        return "更新用户成功。";
    }

    // 查询。
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String find() {
        return "查询用户成功。";
    }

    /**
     * - 传统登录。
     * 前端发送登录请求 -> 接口部分获取用户名密码 -> 程序员在接口部分手动控制。
     * - shiro 登录。
     * 前端发送登录请求 -> 接口部分获取用户名密码 -> 通过 subject.login(); => realm 域的认证方法。
     */
    // 用户登录。
    @RequestMapping(value = "/login")
    public String login(String username, String password) {
        try {
            /**
             * 密码加密：
             *      shiro 提供的 md5 加密。
             *      Md5Hash:
             *      参数一：加密的内容。
             *          111111 ~ abcd
             *      参数二：盐（加密的混淆字符串）（一般可以使用户登录的用户名）。
             *          111111 + 混淆字符串
             *      参数三：加密次数。
             */
            password = new Md5Hash(password, username, 3).toString();

            // 构造登录令牌。
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
            // 获取 subject。
            Subject subject = SecurityUtils.getSubject();

            // 获取 session。
            String sessionId = (String) subject.getSession().getId();

            // 调用 subject 进行登录。
            subject.login(upToken);
            // 用户名和密码以 UsernamePasswordToken 的形式传递给 protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection);。

            return "登录成功。" + sessionId;
        } catch (Exception e) {
            e.printStackTrace();
            return "用户名或密码错误。";
        }
    }

    @RequestMapping("/autherror")
    public String autherror(int code) {
        return code == 1 ? "未登录。" : "未授权。";
    }

    // SessionManager（会话管理器）：管理所有 Subject 的session 包括创建、维护、删除、失效、验证等工作。
    // SessionManager 是顶层组件， 由 SecurityManager 管理。
    // shiro 提供了三个默认实现。
    // - DefaultSessionManager：用于 JavaSE 环境。
    // - ServletContainerSessionManager 用于 Web 环境。直接使用 servlet 容器的会话。
    // - DefaultWebSessionManager： 用于 web 环境，自己维护会话（自己维护着会话，直接废弃了 Servlet 容器的会话管理）。

    // 登录成功后，打印所有 session 内容。
    @RequestMapping("/show")
    public String show(HttpSession session) {
        // 获取 Session 中的所有键值。
        Enumeration<String> enumeration = session.getAttributeNames();
        // 遍历。
        while (enumeration.hasMoreElements()) {
            // 获取 session 的值。
            String name = enumeration.nextElement();
            // 根据键取 session 中的值。
            Object value = session.getAttribute(name);
            System.out.println("name = " + name);
            System.out.println("value = " + value);
        }
        return "查看 session 成功。";
    }
}
