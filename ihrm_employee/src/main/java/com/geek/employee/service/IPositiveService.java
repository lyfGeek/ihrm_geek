package com.geek.employee.service;

import com.geek.domain.employee.EmployeePositive;

public interface IPositiveService {

    EmployeePositive findById(String uid, Integer status);

    EmployeePositive findById(String uid);

    void save(EmployeePositive positive);
}
