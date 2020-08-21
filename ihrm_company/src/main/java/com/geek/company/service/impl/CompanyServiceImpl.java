package com.geek.company.service.impl;

import com.geek.common.utils.IdWorker;
import com.geek.company.dao.ICompanyDao;
import com.geek.company.service.ICompanyService;
import com.geek.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存企业。
     * <p>
     * // 配置 IdWorker 到 Spring。
     * // 在 Service 层注入。
     * // 通过 IdWorker 生成 id。
     * // 保存企业。
     *
     * @param company
     */
    @Override
    public void add(Company company) {
        String id = idWorker.nextId() + "";
        company.setId(id);
        // 默认的状态。
        company.setAuditState("0");// 0 未审核。1 已审核。
        company.setState(0);// 0 未激活。1 已激活。
        companyDao.save(company);
    }


    /**
     * 更新企业。
     *
     * @param company
     */
    @Override
    public void update(Company company) {
        Company temp = companyDao.findById(company.getId()).get();
        temp.setName(company.getName());
        temp.setCompanyPhone(company.getCompanyPhone());
        companyDao.save(temp);
    }

    /**
     * 删除企业。
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    /**
     * 根据 id 查询企业。
     *
     * @param id
     * @return
     */
    @Override
    public Company findById(String id) {
        return companyDao.findById(id).get();
    }

    /**
     * 查询企业列表。
     *
     * @return
     */
    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }
}
