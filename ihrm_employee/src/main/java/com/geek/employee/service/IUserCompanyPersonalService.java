package com.geek.employee.service;

import com.geek.domain.employee.UserCompanyPersonal;
import com.geek.domain.employee.response.EmployeeReportResult;

import java.util.List;

public interface IUserCompanyPersonalService {

    void save(UserCompanyPersonal personalInfo);

    UserCompanyPersonal findById(String userId);

    List<EmployeeReportResult> findByReport(String companyId, String month);

//    List<EmployeeReportResult> findByReport(String companyId, String month);
}
