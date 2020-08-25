package com.geek.employee.service.impl;

import com.geek.domain.employee.EmployeeTransferPosition;
import com.geek.employee.dao.ITransferPositionDao;
import com.geek.employee.service.ITransferPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransferPositionServiceImpl implements ITransferPositionService {

    @Autowired
    private ITransferPositionDao transferPositionDao;

    @Override
    public EmployeeTransferPosition findById(String uid, Integer status) {
        EmployeeTransferPosition transferPosition = transferPositionDao.findByUserId(uid);
        if (status != null && transferPosition != null) {
            if (transferPosition.getEstatus() != status) {
                transferPosition = null;
            }
        }
        return transferPosition;
    }

    @Override
    public void save(EmployeeTransferPosition transferPosition) {
        transferPosition.setCreateTime(new Date());
        transferPosition.setEstatus(1);// 未执行。
        transferPositionDao.save(transferPosition);
    }
}
