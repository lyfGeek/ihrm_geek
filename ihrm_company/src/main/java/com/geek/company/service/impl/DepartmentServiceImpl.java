package com.geek.company.service.impl;

import com.geek.common.service.BaseService;
import com.geek.common.utils.IdWorker;
import com.geek.company.dao.IDepartmentDao;
import com.geek.company.service.IDepartmentService;
import com.geek.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl extends BaseService implements IDepartmentService {
//public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private IDepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存部门。
     *
     * @param department
     */
    @Override
    public void save(Department department) {
        // 设置主键的值。
        String id = idWorker.nextId() + "";
        department.setId(id);
        // 保存。
        departmentDao.save(department);
    }

    /**
     * 更新部门。
     *
     * @param department
     */
    @Override
    public void update(Department department) {
        // 根据 id 查询部门。
        Optional<Department> optional = departmentDao.findById(department.getId());
        Department dept = optional.orElse(null);
        // 设置部门属性。
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        departmentDao.save(dept);
    }

    /**
     * 查询全部部门列表。
     *
     * @return
     */
    @Override
    public List<Department> findAll(String companyId) {

        // 只查询 companyId。
        // 很多地址都需要根据 companyId 查询。
        // 很多对象中都具有 companyId。
        Specification<Department> specification = new Specification<Department>() {
            /**
             * 用户构造查询条件。
             * @param root              包含了所有对象属性。
             * @param criteriaQuery     一般不用。
             * @param criteriaBuilder   构造查询条件。
             * @return
             */
            @Override
            public Predicate toPredicate(javax.persistence.criteria.Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 根据企业 id 查询。
                return criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
            }
        };

        return departmentDao.findAll(specification);
//        return departmentDao.findAll(getSpecification(companyId));
    }

    /**
     * 根据 id 删除部门。
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        departmentDao.deleteById(id);
    }

    /**
     * 根据 id 查询部门。
     *
     * @param id
     */
    @Override
    public Department findById(String id) {
        return departmentDao.findById(id).get();
    }
}
