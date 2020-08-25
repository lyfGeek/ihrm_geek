package com.geek.employee.service;

import com.geek.domain.employee.UserCompanyJobs;

public interface IUserCompanyJobsService {

    void save(UserCompanyJobs jobsInfo);

    UserCompanyJobs findById(String userId);
}
