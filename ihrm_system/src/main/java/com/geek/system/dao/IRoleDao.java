package com.geek.system.dao;

import com.geek.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
}
