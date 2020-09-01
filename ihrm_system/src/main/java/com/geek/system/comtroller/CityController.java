package com.geek.system.comtroller;

import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import com.geek.domain.system.City;
import com.geek.system.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
// has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
@RestController
@RequestMapping("/sys/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    /**
     * 保存。
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody City city) {
        cityService.add(city);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 更新。
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody City city) {
        city.setId(id);
        cityService.update(city);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 删除。
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id) {
        cityService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 查询。
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) throws CommonException {

//        throw new CommonException(ResultCode.UNAUTHENRISE);
//
        City city = cityService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(city);
        return result;
    }

    /**
     * 查询全部。
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
//        int i = 1 / 0;
        List<City> cityList = cityService.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(cityList);
        return result;
    }
}
