package com.geek.company.controller;

import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import com.geek.company.service.ICompanyService;
import com.geek.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
// has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    /**
     * 保存企业。
     *
     * @param company
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Company company) {
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 更新企业。
     *
     * @param id
     * @param company
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody Company company) {
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 删除企业。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id) {
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 查询企业。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) throws CommonException {

//        throw new CommonException(ResultCode.UNAUTHENRISE);
//
        Company company = companyService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(company);
        return result;
    }

    /**
     * 查询全部。
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
//        int i = 1 / 0;
        List<Company> companyList = companyService.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(companyList);
        return result;
    }
}
