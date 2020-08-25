package com.geek.employee.service.impl;

import com.geek.domain.employee.EmployeeResignation;
import com.geek.employee.dao.IEmployeeResignationDao;
import com.geek.employee.service.IResignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResignationServiceImpl implements IResignationService {

    @Autowired
    private IEmployeeResignationDao resignationDao;

    @Override
    public void save(EmployeeResignation resignation) {
        resignation.setCreateTime(new Date());
        resignationDao.save(resignation);
    }

    @Override
    public EmployeeResignation findById(String userId) {
        return resignationDao.findByUserId(userId);
    }
}
