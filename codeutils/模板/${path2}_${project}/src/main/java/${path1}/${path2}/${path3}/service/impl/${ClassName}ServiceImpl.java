<#assign classNameLower=ClassName?uncap_first>
package ${pPackage}.service.impl;

import com.geek.common.utils.IdWorker;
import ${pPackage}.dao.I${ClassName}Dao;
import ${pPackage}.service.I${ClassName}Service;
import ${pPackage}.pojo.${ClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${ClassName}ServiceImpl implements I${ClassName}Service {

    @Autowired
    private I${ClassName}Dao ${classNameLower}Dao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存。
     */
    @Override
    public void add(${ClassName} ${classNameLower}) {
        // 基本属性设置。
        String id = idWorker.nextId() + "";
        ${classNameLower}.setId(id);
        ${classNameLower}Dao.save(${classNameLower});
    }

    /**
     * 删除。
     */
    @Override
    public void deleteById(String id) {
        ${classNameLower}Dao.deleteById(id);
    }

    /**
     * 更新。
     */
    @Override
    public void update(${ClassName} ${classNameLower}) {
        ${classNameLower}Dao.save(${classNameLower});
    }

    /**
     * 根据 id 查询。
     */
    @Override
    public ${ClassName} findById(String id) {
        return ${classNameLower}Dao.findById(id).get();
    }

    /**
     * 查询列表。
     */
    @Override
    public List<${ClassName}> findAll() {
        return ${classNameLower}Dao.findAll();
    }
}
