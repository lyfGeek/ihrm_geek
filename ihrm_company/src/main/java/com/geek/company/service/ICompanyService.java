package com.geek.company.service;

import com.geek.domain.company.Company;

import java.util.List;

public interface ICompanyService {

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
    void add(Company company);

    /**
     * 更新企业。
     *
     * @param company
     */
    void update(Company company);

    /**
     * 删除企业。
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据 id 查询企业。
     *
     * @param id
     * @return
     */
    Company findById(String id);

    /**
     * 查询企业列表。
     *
     * @return
     */
    List<Company> findAll();
}
