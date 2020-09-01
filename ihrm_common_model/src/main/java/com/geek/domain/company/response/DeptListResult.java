package com.geek.domain.company.response;

import com.geek.domain.company.Company;
import com.geek.domain.company.Department;
import lombok.Data;

import java.util.List;

@Data
public class DeptListResult {
    private String companyId;
    private String companyName;
    private String companyManage;
    private List<Department> depts;

    public DeptListResult(Company company, List<Department> departmentList) {
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getLegalRepresentative();
        this.depts = departmentList;
    }
}
