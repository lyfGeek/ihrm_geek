package com.geek.employee.service;

import com.geek.domain.employee.UserCompanyPersonal;

public interface IUserCompanyPersonalService {

    void save(UserCompanyPersonal personalInfo);

    UserCompanyPersonal findById(String userId);

//    List<EmployeeReportResult> findByReport(String companyId, String month);
}
