package com.geek.system.dao;

import com.geek.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IPermissionDao extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {

    List<Permission> findByTypeAndPid(int type, String pid);
}
