package com.geek.system.service.impl;

import com.geek.common.utils.IdWorker;
import com.geek.domain.system.City;
import com.geek.system.dao.ICityDao;
import com.geek.system.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements ICityService {

    @Autowired
    private ICityDao cityDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存。
     */
    @Override
    public void add(City city) {
        // 基本属性设置。
        String id = idWorker.nextId() + "";
        city.setId(id);
        cityDao.save(city);
    }

    /**
     * 删除。
     */
    @Override
    public void deleteById(String id) {
        cityDao.deleteById(id);
    }

    /**
     * 更新。
     */
    @Override
    public void update(City city) {
        cityDao.save(city);
    }

    /**
     * 根据 id 查询。
     */
    @Override
    public City findById(String id) {
        return cityDao.findById(id).get();
    }

    /**
     * 查询列表。
     */
    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }
}
