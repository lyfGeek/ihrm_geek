package com.geek.employee.dao;

import com.geek.domain.employee.EmployeePositive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口。
 */
public interface IPositiveDao extends JpaRepository<EmployeePositive, String>, JpaSpecificationExecutor<EmployeePositive> {

    EmployeePositive findByUserId(String uid);
}
