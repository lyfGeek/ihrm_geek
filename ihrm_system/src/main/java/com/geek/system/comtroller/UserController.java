package com.geek.system.comtroller;

import com.geek.common.controller.BaseController;
import com.geek.common.entity.PageResult;
import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import com.geek.common.poi.ExcelImportUtil;
import com.geek.common.utils.JwtUtils;
import com.geek.domain.system.User;
import com.geek.domain.system.response.ProfileResult;
import com.geek.domain.system.response.UserResult;
import com.geek.system.client.IDepartmentFeignClient;
import com.geek.system.service.IPermissionService;
import com.geek.system.service.IUserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
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

    @Autowired
    private IDepartmentFeignClient departmentFeignClient;

    /**
     * 读取解析 Excel 数据。
     *
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        // 获取单元格的属性类型。
        CellType cellType = cell.getCellType();
        // 根据单元格数据类型获取数据。
        Object cellValue = null;

        switch (cellType) {
            case NUMERIC:// = 0; 数字（日期、普通数字等）。
//                System.out.print("【~ ~ ~ CELL_TYPE ~ numeric ～ 0 ~ ~ ~】");
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 日期。
//                    System.out.print("【~ ~ ~ CELL_TYPE ~ numeric ～ 0 ～ 日期。~ ~ ~】");
                    cellValue = cell.getDateCellValue();
                } else {
//                    System.out.print("【~ ~ ~ CELL_TYPE ~ numeric ～ 0 ～ 转换为字符串输出。~ ~ ~】");
//                     如果不是日期格式，防止数字过长。
//                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cellValue = cell.getNumericCellValue();
                }
                break;

            case STRING:// = 1;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ String ～ 1 ~ ~ ~】");
                cellValue = cell.getStringCellValue();
                break;

            case FORMULA:// = 2;
//                System.out.println("~ ~ ~ CELL_TYPE ~ formula ～ 2 ~ ~ ~");
                cellValue = cell.getCellFormula();
                break;

//            case HSSFCell.CELL_TYPE_BLANK:// = 3;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ blank ～ 3 ~ ~ ~】");
//                break;

            case BOOLEAN:// = 4;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ boolean ～ 4 ~ ~ ~】");
                cellValue = cell.getBooleanCellValue();
                break;

//            case HSSFCell.CELL_TYPE_ERROR:// = 5;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ error ～ 5 数据类型错误。~ ~ ~】");
//                cellValue = String.valueOf(cell.getErrorCellValue());
//                break;

            default:
                break;
        }

        return cellValue;
    }

    /**
     * 通过 DataUrl 形式上传到数据。/七牛云在 Service 层改造。
     *
     * @param id
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/upload/{id}")
    public Result upload(@PathVariable String id,
                         @RequestParam("file") MultipartFile multipartFile) throws IOException {
        // 调用 Service 保存图片。获取图片访问地址。dataUrl 或 http 地址。
        String imgUrl = userService.uploadImage(id, multipartFile);
        // 返回数据。
        return new Result(ResultCode.SUCCESS, imgUrl);
        // {success: true, code: null, message: "操作成功。", data: "data:image/png;base64,[B@108319af"}
        //success: true
        //code: null
        //message: "操作成功。"
        //data: "data:image/png;base64,[B@108319af"
    }

    /**
     * 通过 Excel 批量上传用户信息。
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/user/import")
    public Result importUser(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        // - 解析 excel。
//        // 根据 excel 文件创建工作薄。
//        Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
//        // 获取 sheet。
//        Sheet sheet = workbook.getSheetAt(0);
//        // 获取 sheet 的每一行，和每一个单元格。
//
//        // 获取用户数据列表。
//        List<User> list = new ArrayList<>();
//
//        for (int rowNum = 1; rowNum < sheet.getLastRowNum() + 1; rowNum++) {
//            Row row = sheet.getRow(rowNum);// 根据索引获取每一行。
//
//            Object[] values = new Object[row.getLastCellNum()];
//
//            for (int cellNum = 1; cellNum < row.getLastCellNum(); ++cellNum) {
//                Cell cell = row.getCell(cellNum);// 根据索引获取每一单元格。
//                // 获取每一个单元格的内容。
//                Object cellValue = getCellValue(cell);
//                values[cellNum] = cellValue;
//            }
//            User user = new User(values);
//            list.add(user);
//        }

        // java.lang.IllegalAccessException: Class com.geek.common.poi.ExcelImportUtil can not access a member of class com.geek.domain.system.User with modifiers "private"
        List<User> list1 = new ExcelImportUtil(User.class).readExcel(multipartFile.getInputStream(), 1, 1);

        // 批量保存用户。
        userService.saveAll(list1, companyId, companyName);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 测试 feign 组件。
     *
     * @param id
     * @return
     */
    @GetMapping("/test/{id}")
    public Result test(@PathVariable("id") String id) {
        // 调用系统微服务的接口。
        return departmentFeignClient.findById(id);
    }

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
     * @param userId
     * @param user
     * @return
     */
    @PutMapping("/user/{userId}")
    public Result update(@PathVariable("userId") String userId, @RequestBody User user) {
        user.setId(userId);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 删除用户。
     *
     * @param userId
     * @return
     */
    @RequiresPermissions("API-USER-DELETE")
    @DeleteMapping(value = "/user/{userId}", name = "API-USER-DELETE")
    public Result delete(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
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

        try {
            // 构造登录令牌。UsernamePasswordToken。
            password = new Md5Hash(password, mobile, 3).toString();// 密码，盐，加密次数。
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(mobile, password);

            // 获取 subject。
            Subject subject = SecurityUtils.getSubject();

            // 调用 login(); 进入 com.geek.system.shiro.realm.UserRealm 完成认证。
            subject.login(usernamePasswordToken);

            // 获取 sessionId。
            Serializable sessionId = subject.getSession().getId();
            sessionId = sessionId.toString();

            // 构造返回结果。
            return new Result(ResultCode.SUCCESS, sessionId);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result(ResultCode.MOBILE_OR_PASSWORD_ERROR);
        }

        // shiro 登录方式。↑ ↑ ↑。
        // jwt 登录方式。↓ ↓ ↓。

//        User user = userService.findByMobile(mobile);
//        // 登录失败。
//        if (user == null || !password.equals(user.getPassword())) {
//            return new Result(ResultCode.MOBILE_OR_PASSWORD_ERROR);
//        } else {
//            // 登录成功。
//
//            // API 权限字符串。
//            StringBuilder stringBuilder = new StringBuilder();
//            // 获取所有 api 权限。
//            for (Role role : user.getRoles()) {
//                for (Permission permission : role.getPermissions()) {
//                    stringBuilder.append(permission.getCode()).append(", ");
//                }
//            }
//
//            Map<String, Object> map = new HashMap<>();
//
//            map.put("apis", stringBuilder.toString());
//
//            map.put("companyId", user.getCompanyId());
//            map.put("companyName", user.getCompanyName());
//            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
//            return new Result(ResultCode.SUCCESS, token);
//        }
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

        /*
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
        */
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

        // shiro。
        // 获取 session 中的安全数据。
        Subject subject = SecurityUtils.getSubject();
        // 获取所有安全数据集合。
        PrincipalCollection principals = subject.getPrincipals();
        // 获取安全数据。
        ProfileResult profileResult = (ProfileResult) principals.getPrimaryPrincipal();

        return new Result(ResultCode.SUCCESS, profileResult);
    }
}
