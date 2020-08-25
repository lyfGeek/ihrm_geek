package com.geek.employee.service;

import com.geek.domain.employee.EmployeeTransferPosition;

public interface ITransferPositionService {

    EmployeeTransferPosition findById(String uid, Integer status);

    void save(EmployeeTransferPosition transferPosition);
}
