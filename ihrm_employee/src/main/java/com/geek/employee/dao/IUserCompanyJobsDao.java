package com.geek.employee.dao;

import com.geek.domain.employee.UserCompanyJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口。
 */
public interface IUserCompanyJobsDao extends JpaRepository<UserCompanyJobs, String>, JpaSpecificationExecutor<UserCompanyJobs> {

    UserCompanyJobs findByUserId(String userId);
}
