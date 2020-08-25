package com.geek.employee.service.impl;

import com.geek.domain.employee.UserCompanyJobs;
import com.geek.employee.dao.IUserCompanyJobsDao;
import com.geek.employee.service.IUserCompanyJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCompanyJobsServiceImpl implements IUserCompanyJobsService {

    @Autowired
    private IUserCompanyJobsDao userCompanyJobsDao;

    @Override
    public void save(UserCompanyJobs jobsInfo) {
        userCompanyJobsDao.save(jobsInfo);
    }

    @Override
    public UserCompanyJobs findById(String userId) {
        return userCompanyJobsDao.findByUserId(userId);
    }
}
