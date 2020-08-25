package com.geek.employee.dao;

import com.geek.domain.employee.UserCompanyPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口。
 */
public interface IUserCompanyPersonalDao extends JpaRepository<UserCompanyPersonal, String>, JpaSpecificationExecutor<UserCompanyPersonal> {

    UserCompanyPersonal findByUserId(String userId);

//    @Query(value = "select new com.ihrm.domain.employee.response.EmployeeReportResult(a,b) from UserCompanyPersonal a " +
//            "LEFT JOIN EmployeeResignation b on a.userId = b.userId where a.companyId = ?1 and a.timeOfEntry " +
//            "like ?2 or (b.resignationTime like ?2)")
//    List<EmployeeReportResult> findByReport(String companyId, String month);
}
