package com.geek.system.client;

import com.geek.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 声明接口，通过 feign 调用其他微服务。
 */
@FeignClient("ihrm-company")
public interface DepartmentFeignClient {

    /**
     * 根据 id 查询部门。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/company/department/{departmentId}", method = RequestMethod.GET)
    Result findById(@PathVariable("departmentId") String id);

}
