package com.geek.employee.dao;

import com.geek.domain.employee.UserCompanyPersonal;
import com.geek.domain.employee.response.EmployeeReportResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口。
 */
public interface IUserCompanyPersonalDao extends JpaRepository<UserCompanyPersonal, String>, JpaSpecificationExecutor<UserCompanyPersonal> {

    UserCompanyPersonal findByUserId(String userId);

    //    @Query(value = "select new com.geek.domain.employee.response.EmployeeReportResult(a, b) from UserCompanyPersonal a " +
//            "LEFT JOIN " +
//            "        EmployeeResignation b ON a.userId = b.userId " +
//            "    WHERE " +
//            "        a.companyId = ?1 and a.timeOfEntry LIKE ?2 " +
//            "        OR b.resignationTime LIKE ?2")
    @Query(value = "select new com.geek.domain.employee.response.EmployeeReportResult(a, b) from UserCompanyPersonal a " +
            "LEFT JOIN " +
            "        EmployeeResignation b ON a.userId = b.userId " +
            "    WHERE " +
            "        a.companyId = ?1 and a.timeOfEntry LIKE ?2 " +
            "        OR b.resignationTime LIKE ?2")
    List<EmployeeReportResult> findByReport(String companyId, String month);

    // SELECT
    //    *
    //FROM
    //    ihrm.em_user_company_personal a
    //        LEFT JOIN
    //    em_resignation b ON a.user_id = b.user_id
    //WHERE
    //    a.time_of_entry LIKE '2018-02%'
    //        OR b.resignation_time LIKE '2018-02%';
}
