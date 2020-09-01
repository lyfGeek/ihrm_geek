<#assign classNameLower=ClassName?uncap_first>
package ${pPackage}.service;

import ${pPackage}.pojo.Company;

import java.util.List;

public interface I${ClassName}Service {

    /**
     * 保存。
     */
    void add(${ClassName} ${classNameLower});

    /**
     * 删除。
     */
    void deleteById(String id);

    /**
     * 更新。
     */
    void update(${ClassName} ${classNameLower});

    /**
     * 根据 id 查询。
     */
    ${ClassName} findById(String id);

    /**
     * 查询列表。
     */
    List<${ClassName}> findAll();
}
