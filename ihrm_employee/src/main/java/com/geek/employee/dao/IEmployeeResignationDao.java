package com.geek.employee.dao;

import com.geek.domain.employee.EmployeeResignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口。
 */
public interface IEmployeeResignationDao extends JpaRepository<EmployeeResignation, String>, JpaSpecificationExecutor<EmployeeResignation> {

    EmployeeResignation findByUserId(String uid);
}
