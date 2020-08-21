package com.geek.system.comtroller;

import com.geek.common.controller.BaseController;
import com.geek.common.entity.PageResult;
import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import com.geek.common.utils.JwtUtils;
import com.geek.domain.system.Permission;
import com.geek.domain.system.Role;
import com.geek.domain.system.User;
import com.geek.domain.system.response.ProfileResult;
import com.geek.domain.system.response.UserResult;
import com.geek.system.service.IPermissionService;
import com.geek.system.service.IUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 分配权限。
     *
     * @param map
     * @return
     */
    @PutMapping("/user/assignRoles")
    public Result save(@RequestBody Map<String, Object> map) {
        // 获取到被分配的用户 id。
        String userId = (String) map.get("id");
        // 获取到角色的 id 列表。
        List<String> roleIds = (List<String>) map.get("roleIds");
        // 调用 Service 完成角色分配。
        userService.assignRoles(userId, roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 新增用户。
     *
     * @return
     */
    @PostMapping("/user")
    public Result save(@RequestBody User user) {
        // 用户所属企业。
//        String companyId = "1";
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        // 调用 Service。
        userService.save(user);
        // 返回结果。
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的所有用户。
     * （根据企业 id）。
     *
     * @return
     */
    @GetMapping("/user")
    public Result findAll(int page, int size, @RequestParam Map map) {
        // 获取当前企业 id。
        map.put("companyId", companyId);
        // 查询。
        Page<User> userPage = userService.findAll(map, page, size);
        // 构造返回结果。
        PageResult<User> pageResult = new PageResult<>(userPage.getTotalElements(), userPage.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据 id 查询用户。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Result queryAll(@PathVariable("userId") String id) {
        // 添加 roleIds（用户已经具有的角色 id 数组）。
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
    }

    /**
     * 修改用户。
     *
     * @param id
     * @return
     */
    @PutMapping("/user/{userId}")
    public Result update(@PathVariable("userId") String id, @RequestBody User user) {
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 删除用户。
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/user/{userId}", name = "API-USER-DELETE")
    public Result delete(@PathVariable("userId") String id) {
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 用户登录。
     * <p>
     * - 通过 service 查询用户。
     * - 比较 password。
     * - 生成 jwt 信息。
     *
     * @param loginMap
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.findByMobile(mobile);
        // 登录失败。
        if (user == null || !password.equals(user.getPassword())) {
            return new Result(ResultCode.MOBILE_OR_PASSWORD_ERROR);
        } else {
            // 登录成功。

            // API 权限字符串。
            StringBuilder stringBuilder = new StringBuilder();
            // 获取所有 api 权限。
            for (Role role : user.getRoles()) {
                for (Permission permission : role.getPermissions()) {
                    stringBuilder.append(permission.getCode()).append(", ");
                }
            }

            Map<String, Object> map = new HashMap<>();

            map.put("apis", stringBuilder.toString());

            map.put("companyId", user.getCompanyId());
            map.put("companyName", user.getCompanyName());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS, token);
        }
    }

    /**
     * 用户登录成功后，获取用户信息。
     * - 获取用户 id。
     * - 根据用户 id 查询用户。
     * - 构造返回对象。
     * - 响应。
     *
     * @return
     */
    @PostMapping("/profile")
    public Result profile(HttpServletRequest request) throws CommonException {
        /*
        // 从请求头信息中获取 token 数据。
        // 获取请求头信息。名称=Authorization。
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        // 替换 Bearer + 空格。
        String token = authorization.replace("Bearer ", "");
        // 解析 token。
        // 获取 claims。

        Claims claims = jwtUtils.parseJwt(token);
        //        String userId = "1";
        */
        Claims claims = (Claims) request.getAttribute("user_claims");

        String userId = claims.getId();
        // 获取用户信息。
        User user = userService.findById(userId);

        // 根据不同的用户级别获得用户权限。

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

        // 简化 ↑ ↑ ↑。
/*        // saas 平台管理员具有所有权限。
        if ("saasAdmin".equals(user.getLevel())) {
            Map map = new HashMap();
            List<Permission> list = permissionService.findAll(map);
        } else if ("coAdmin".equals(user.getLevel())) {
            // 企业管理员具有所有的企业权限。
            Map map = new HashMap();
            List<Permission> list = permissionService.findAll(map);
        } else {
            // 企业用户具有当前角色的权限。
            profileResult = new ProfileResult(user);
        }*/
        return new Result(ResultCode.SUCCESS, profileResult);
    }
}
