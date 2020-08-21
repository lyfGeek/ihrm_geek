package com.geek.system.comtroller;

import com.geek.common.controller.BaseController;
import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import com.geek.domain.system.Permission;
import com.geek.system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys")
public class PermissionController extends BaseController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 新增。
     *
     * @param map
     * @return
     * @throws IllegalAccessException
     * @throws CommonException
     * @throws InstantiationException
     */
    @PostMapping("/permission")
    public Result save(@RequestBody Map<String, Object> map) throws IllegalAccessException, CommonException, InstantiationException {
        permissionService.save(map);
        // 返回结果。
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询列表。
     *
     * @return
     */
    @GetMapping("/permission")
    public Result findAll(@RequestParam Map map) {
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS, list);
    }

    /**
     * 根据 id 查询。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public Result queryAll(@PathVariable("id") String id) throws CommonException {
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS, map);
    }

    /**
     * 修改。
     *
     * @param id
     * @return
     */
    @PutMapping("/permission/{id}")
    public Result update(@PathVariable("id") String id, @RequestBody Map<String, Object> map) throws IllegalAccessException, CommonException, InstantiationException {
        // 构造 id。
        map.put("id", id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 删除。
     *
     * @param id
     * @return
     */
    @DeleteMapping("/permission/{userId}")
    public Result delete(@PathVariable("userId") String id) {
        return new Result(ResultCode.SUCCESS);
    }
}
