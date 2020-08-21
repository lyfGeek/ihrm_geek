package com.geek.system.comtroller;

import com.geek.common.controller.BaseController;
import com.geek.common.entity.PageResult;
import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.domain.system.Role;
import com.geek.domain.system.response.RoleResult;
import com.geek.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @PutMapping("/role/assignRoles")
    public Result save(@RequestBody Map<String, Object> map) {
        // 获取到被分配的用户 id。
        String roleId = (String) map.get("id");
        // 获取到角色的 id 列表。
        List<String> permIds = (List<String>) map.get("permIds");
        // 调用 Service 完成角色分配。
        roleService.assignPerms(roleId, permIds);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 新增角色。
     *
     * @param role
     * @return
     */
    @PostMapping("/role")
    public Result add(@RequestBody Role role) {
        role.setCompanyId(companyId);
        roleService.save(role);
        return Result.SUCCESS();
    }

    /**
     * 更新角色。
     *
     * @param id
     * @param role
     * @return
     */
    @PutMapping("/role/{id}")
    public Result update(@PathVariable("id") String id, @RequestBody Role role) {
        roleService.update(role);
        return Result.SUCCESS();
    }

    /**
     * 删除角色。
     *
     * @param id
     * @return
     */
    @DeleteMapping("/role/{id}")
    public Result delete(@PathVariable("id") String id) {
        roleService.delete(id);
        return Result.SUCCESS();
    }

    /**
     * 根据 id 获取角色信息。
     *
     * @param id
     * @return
     */
    @GetMapping("/role/{id}")
    public Result findById(@PathVariable("id") String id) {
        Role role = roleService.findById(id);
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS, roleResult);
    }

    /**
     * 分页查询角色。
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/role")
    public Result findByPage(int page, int pageSize) {
        Page<Role> rolePage = roleService.findByPage(companyId, page, pageSize);
        PageResult<Role> rolePageResult = new PageResult(rolePage.getTotalElements(), rolePage.getContent());
        return new Result(ResultCode.SUCCESS, rolePageResult);
    }
}
