package com.geek.employee.service.impl;

import com.geek.common.service.BaseService;
import com.geek.common.utils.IdWorker;
import com.geek.domain.employee.EmployeeArchive;
import com.geek.employee.dao.IArchiveDao;
import com.geek.employee.service.IArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ArchiveServiceImpl extends BaseService implements IArchiveService {

    @Autowired
    private IArchiveDao archiveDao;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void save(EmployeeArchive archive) {
        archive.setId(idWorker.nextId() + "");
        archive.setCreateTime(new Date());
        archiveDao.save(archive);
    }

    @Override
    public EmployeeArchive findLast(String companyId, String month) {
        return archiveDao.findByLast(companyId, month);
    }

    @Override
    public List<EmployeeArchive> findAll(Integer page, Integer pageSize, String year, String companyId) {
        int index = (page - 1) * pageSize;
        return archiveDao.findAllData(companyId, year + "%", index, pageSize);
    }

    @Override
    public Long countAll(String year, String companyId) {
        return archiveDao.countAllData(companyId, year + "%");
    }

    @Override
    public Page<EmployeeArchive> findSearch(Map<String, Object> map, int page, int size) {
        return archiveDao.findAll(createSpecification(map), PageRequest.of(page - 1, size));
    }

    private Specification<EmployeeArchive> createSpecification(Map searchMap) {
        return new Specification<EmployeeArchive>() {
            @Override
            public Predicate toPredicate(Root<EmployeeArchive> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 企业 id。
                if (searchMap.get("companyId") != null && !"".equals(searchMap.get("companyId"))) {
                    predicateList.add(cb.like(root.get("companyId").as(String.class), (String) searchMap.get("companyId")));
                }
                if (searchMap.get("year") != null && !"".equals(searchMap.get("year"))) {
                    predicateList.add(cb.like(root.get("mouth").as(String.class), (String) searchMap.get("year")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
