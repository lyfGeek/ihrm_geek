package com.geek.employee.service.impl;

import com.geek.domain.employee.UserCompanyPersonal;
import com.geek.domain.employee.response.EmployeeReportResult;
import com.geek.employee.dao.IUserCompanyPersonalDao;
import com.geek.employee.service.IUserCompanyPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCompanyPersonalServiceImpl implements IUserCompanyPersonalService {

    @Autowired
    private IUserCompanyPersonalDao userCompanyPersonalDao;

    @Override
    public void save(UserCompanyPersonal personalInfo) {
        userCompanyPersonalDao.save(personalInfo);
    }

    @Override
    public UserCompanyPersonal findById(String userId) {
        return userCompanyPersonalDao.findByUserId(userId);
    }

    @Override
    public List<EmployeeReportResult> findByReport(String companyId, String month) {
        return userCompanyPersonalDao.findByReport(companyId, "2018-" + month + "%");
    }

//    @Override
//    public List<EmployeeReportResult> findByReport(String companyId, String month) {
//        return userCompanyPersonalDao.findByReport(companyId, month + "%");
//    }
}
