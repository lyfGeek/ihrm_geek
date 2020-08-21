package com.geek.system.service;

import com.geek.domain.system.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {

    /**
     * 保存角色。
     *
     * @param role
     */
    void save(Role role);

    /**
     * 更新角色。
     *
     * @param role
     */
    void update(Role role);

    /**
     * 根据 id 查询角色。
     *
     * @param id
     * @return
     */
    Role findById(String id);

    /**
     * 查询所有角色。
     *
     * @param companyId
     * @return
     */
    List<Role> findAll(String companyId);

    /**
     * 根据 id 删除角色。
     *
     * @param id
     */
    void delete(String id);

    /**
     * 查询所有角色。分页。
     *
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    Page<Role> findByPage(String companyId, int page, int size);

    /**
     * 分配权限。
     *
     * @param roleId
     * @param permIds
     */
    void assignPerms(String roleId, List<String> permIds);
}
