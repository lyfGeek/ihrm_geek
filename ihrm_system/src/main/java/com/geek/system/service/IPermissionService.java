package com.geek.system.service;

import com.geek.common.exception.CommonException;
import com.geek.domain.system.Permission;

import java.util.List;
import java.util.Map;

public interface IPermissionService {

    /**
     * 保存权限。
     *
     * @param map
     * @throws CommonException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    void save(Map<String, Object> map) throws CommonException, InstantiationException, IllegalAccessException;

    /**
     * 更新权限。
     *
     * @param map
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws CommonException
     */
    void update(Map<String, Object> map) throws InstantiationException, IllegalAccessException, CommonException;

    /**
     * 查询全部权限列表。
     *
     * @param map
     * @return
     */
    List<Permission> findAll(Map<String, Object> map);

    /**
     * 根据 id 删除权限。
     *
     * @param id
     * @throws CommonException
     */
    void deleteById(String id) throws CommonException;

    /**
     * 根据 id 查询权限。
     *
     * @param id
     * @return
     * @throws CommonException
     */
    Map<String, Object> findById(String id) throws CommonException;
}
