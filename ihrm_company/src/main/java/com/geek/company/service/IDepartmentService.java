package com.geek.company.service;

import com.geek.domain.company.Department;

import java.util.List;

public interface IDepartmentService {

    /**
     * 保存部门。
     *
     * @param department
     */
    void save(Department department);

    /**
     * 更新部门。
     *
     * @param department
     */
    void update(Department department);

    /**
     * 查询全部列表。
     *
     * @return
     */
    List<Department> findAll(String companyId);

    /**
     * 根据 id 删除部门。
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据 id 查询部门。
     *
     * @param id
     */
    Department findById(String id);

    /**
     * 根据部门编码和企业 id 查询部门。
     *
     * @param code
     * @param companyId
     * @return
     */
    Department findByCode(String code, String companyId);
}
