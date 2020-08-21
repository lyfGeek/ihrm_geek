package com.geek.company.controller;

import com.geek.common.controller.BaseController;
import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.company.service.ICompanyService;
import com.geek.company.service.IDepartmentService;
import com.geek.domain.company.Company;
import com.geek.domain.company.Department;
import com.geek.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class DepartmentController extends BaseController {

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ICompanyService companyService;

    /**
     * 增加部门。
     *
     * @return
     */
    @PostMapping("/department")
    public Result save(@RequestBody Department department) {
        // 部门所属企业。
//        String companyId = "1";
        department.setCompanyId(companyId);
        department.setCreateTime(new Date());
        // 调用 Service。
        departmentService.save(department);
        // 返回结果。
        return new Result(ResultCode.SUCCESS);
    }


    /**
     * 查询企业的所有部门。
     * （根据企业 id）。
     *
     * @return
     */
    @GetMapping("/department")
    public Result queryAll() {
        // 部门所属企业。
//        String companyId = "1";
        Company company = companyService.findById(companyId);
        // 查询企业的所有部门。
        List<Department> departmentList = departmentService.findAll(companyId);
        // 构造返回结果。
        DeptListResult deptListResult = new DeptListResult(company, departmentList);
        return new Result(ResultCode.SUCCESS, deptListResult);
    }

    /**
     * 根据 id 查询部门。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/department/{departmentId}", method = RequestMethod.GET)
    public Result findAll(@PathVariable("departmentId") String id) {
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS, department);
    }

    /**
     * 修改部门。
     *
     * @param id
     * @return
     */
    @PutMapping("/department/{departmentId}")
    public Result update(@PathVariable("departmentId") String id, @RequestBody Department department) {
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 删除部门。
     *
     * @param id
     * @return
     */
    @DeleteMapping("/department/{departmentId}")
    public Result delete(@PathVariable("departmentId") String id) {
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
