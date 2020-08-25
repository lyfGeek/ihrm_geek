package com.geek.employee.dao;

import com.geek.domain.employee.EmployeeTransferPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口。
 */
public interface ITransferPositionDao extends JpaRepository<EmployeeTransferPosition, String>, JpaSpecificationExecutor<EmployeeTransferPosition> {

    EmployeeTransferPosition findByUserId(String uid);
}
