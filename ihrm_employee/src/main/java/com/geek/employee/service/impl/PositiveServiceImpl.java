package com.geek.employee.service.impl;

import com.geek.domain.employee.EmployeePositive;
import com.geek.employee.dao.IPositiveDao;
import com.geek.employee.service.IPositiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PositiveServiceImpl implements IPositiveService {

    @Autowired
    private IPositiveDao positiveDao;

    @Override
    public EmployeePositive findById(String uid, Integer status) {
        EmployeePositive positive = positiveDao.findByUserId(uid);
        if (status != null && positive != null) {
            if (positive.getEstatus() != status) {
                positive = null;
            }
        }
        return positive;
    }

    @Override
    public EmployeePositive findById(String uid) {
        return positiveDao.findByUserId(uid);
    }

    @Override
    public void save(EmployeePositive positive) {
        positive.setCreateTime(new Date());
        positive.setEstatus(1);// 未执行。
        positiveDao.save(positive);
    }
}
