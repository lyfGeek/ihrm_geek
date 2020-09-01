package com.geek.system.service;

import com.geek.domain.system.City;

import java.util.List;

public interface ICityService {

    /**
     * 保存。
     */
    void add(City city);

    /**
     * 删除。
     */
    void deleteById(String id);

    /**
     * 更新。
     */
    void update(City city);

    /**
     * 根据 id 查询。
     */
    City findById(String id);

    /**
     * 查询列表。
     */
    List<City> findAll();
}
