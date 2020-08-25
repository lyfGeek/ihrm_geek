package com.geek.employee.service;

import com.geek.domain.employee.EmployeeResignation;

public interface IResignationService {

    void save(EmployeeResignation resignation);

    EmployeeResignation findById(String userId);
}
