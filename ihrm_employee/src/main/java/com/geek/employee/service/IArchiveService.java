package com.geek.employee.service;

import com.geek.domain.employee.EmployeeArchive;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IArchiveService {

    void save(EmployeeArchive archive);

    EmployeeArchive findLast(String companyId, String month);

    List<EmployeeArchive> findAll(Integer page, Integer pagesize, String year, String companyId);

    Long countAll(String year, String companyId);

    Page<EmployeeArchive> findSearch(Map<String, Object> map, int page, int size);
}
