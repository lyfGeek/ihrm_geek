package com.geek.company.dao;

import com.geek.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IDepartmentDao extends JpaRepository<Department, String>, JpaSpecificationExecutor<Department> {
}
