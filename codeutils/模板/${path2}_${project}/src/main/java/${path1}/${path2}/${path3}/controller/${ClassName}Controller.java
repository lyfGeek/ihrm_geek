<#assign classNameLower=ClassName?uncap_first>
package ${pPackage}.controller;

import ${pPackage}.pojo.Result;
import ${pPackage}.pojo.ResultCode;
import com.geek.common.exception.CommonException;
import ${pPackage}.service.I${ClassName}Service;
import ${pPackage}.pojo.${ClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
// has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
@RestController
@RequestMapping("/${classNameLower}")
public class ${ClassName}Controller {

    @Autowired
    private I${ClassName}Service ${classNameLower}Service;

    /**
     * 保存。
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody ${ClassName} ${classNameLower}) {
        ${classNameLower}Service.add(${classNameLower});
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 更新。
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody ${ClassName} ${classNameLower}) {
        ${classNameLower}.setId(id);
        ${classNameLower}Service.update(${classNameLower});
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 删除。
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id) {
        ${classNameLower}Service.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据 id 查询。
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) throws CommonException {

//        throw new CommonException(ResultCode.UNAUTHENRISE);
//
        ${ClassName} ${classNameLower} = ${classNameLower}Service.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(${classNameLower});
        return result;
    }

    /**
     * 查询全部。
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
//        int i = 1 / 0;
        List<${ClassName}> ${classNameLower}List = ${classNameLower}Service.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(${classNameLower}List);
        return result;
    }
}
