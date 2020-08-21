package com.geek.system.service.impl;

import com.geek.common.service.BaseService;
import com.geek.common.utils.IdWorker;
import com.geek.common.utils.PermissionConstants;
import com.geek.domain.system.Permission;
import com.geek.domain.system.Role;
import com.geek.system.dao.IPermissionDao;
import com.geek.system.dao.IRoleDao;
import com.geek.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl extends BaseService implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IPermissionDao permissionDao;

    /**
     * 分配权限。
     *
     * @param roleId
     * @param permIds
     */
    @Override
    public void assignPerms(String roleId, List<String> permIds) {
        // 获取分配的角色对象，
        Role role = roleDao.findById(roleId).get();
        // 构造角色的权限集合。
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId).get();
            // 需要根据父 id 和类型查询 API 权限列表。
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PERMISSION_API, permission.getId());
            perms.addAll(apiList);// 赋予 api 权限。
            perms.add(permission);// 当前菜单或按钮的权限。
        }
        // 设置角色和权限的关系。
        role.setPermissions(perms);
        // 更新角色。
        roleDao.save(role);
    }

    /**
     * 保存角色。
     *
     * @param role
     */
    @Override
    public void save(Role role) {
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);
    }

    /**
     * 更新角色。
     *
     * @param role
     */
    @Override
    public void update(Role role) {
        Role one = roleDao.getOne(role.getId());
        one.setDescription(role.getDescription());
        one.setName(role.getName());
        roleDao.save(one);
    }

    /**
     * 根据 id 查询角色。
     *
     * @param id
     * @return
     */
    @Override
    public Role findById(String id) {
        return roleDao.findById(id).get();
    }

    /**
     * 查询所有角色。
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(getSpecification(companyId));
    }

    /**
     * 根据 id 删除角色。
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        roleDao.deleteById(id);
    }

    /**
     * 查询所有角色。分页。
     *
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Role> findByPage(String companyId, int page, int size) {
        return roleDao.findAll(getSpecification(companyId), PageRequest.of(page - 1, size));
    }
}
