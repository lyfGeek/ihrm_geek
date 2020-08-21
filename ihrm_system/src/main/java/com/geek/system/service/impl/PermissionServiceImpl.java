package com.geek.system.service.impl;

import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import com.geek.common.utils.BeanMapUtils;
import com.geek.common.utils.IdWorker;
import com.geek.common.utils.PermissionConstants;
import com.geek.domain.system.Permission;
import com.geek.domain.system.PermissionApi;
import com.geek.domain.system.PermissionMenu;
import com.geek.domain.system.PermissionPoint;
import com.geek.system.dao.IPermissionApiDao;
import com.geek.system.dao.IPermissionDao;
import com.geek.system.dao.IPermissionMenuDao;
import com.geek.system.dao.IPermissionPointDao;
import com.geek.system.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private IPermissionMenuDao permissionMenuDao;

    @Autowired
    private IPermissionPointDao permissionPointDao;

    @Autowired
    private IPermissionApiDao permissionApiDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存权限。
     *
     * @param map
     * @throws CommonException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public void save(Map<String, Object> map) throws CommonException, InstantiationException, IllegalAccessException {
        // 设置主键的值。
        String id = idWorker.nextId() + "";
        // 通过 map 构造 Permission 对象。
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        // 根据类型构造不同的资源对象。（菜单、按钮、api）。
        Integer type = permission.getType();
        switch (type) {
            case PermissionConstants
                    .PERMISSION_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants
                    .PERMISSION_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants
                    .PERMISSION_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;

            default:
                throw new CommonException(ResultCode.FAIL);
        }
        // 保存。
        permissionDao.save(permission);
    }


    /**
     * 更新权限。
     *
     * @param map
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws CommonException
     */
    @Override
    public void update(Map<String, Object> map) throws InstantiationException, IllegalAccessException, CommonException {
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);

        // 通过传递的权限 id 查询权限。
        Permission permission = permissionDao.findById(perm.getId()).get();
        permission.setName(perm.getName());
        permission.setCode(perm.getCode());
        permission.setDescription(perm.getDescription());
        permission.setEnVisible(perm.getEnVisible());

        // 根据类型构造不同的资源。
        // 根据类型构造不同的资源对象。（菜单、按钮、api）。
        Integer type = permission.getType();
        switch (type) {
            case PermissionConstants
                    .PERMISSION_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(perm.getId());
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants
                    .PERMISSION_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(perm.getId());
                permissionPointDao.save(point);
                break;
            case PermissionConstants
                    .PERMISSION_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(perm.getId());
                permissionApiDao.save(api);
                break;

            default:
                throw new CommonException(ResultCode.FAIL);
        }
        // 保存。
        permissionDao.save(permission);
    }

    /**
     * 查询全部权限列表。
     * <p>
     * type 查询全部权限列表 type。
     * 0 ~ 菜单 + 按钮（权限点）。 1 ~ 菜单。2 ~ 按钮（ 权限点） 3 ： API 接囗
     * enVisib1e
     * 0 ~ 查询所有 saas 平台的最高权限。1 ~ 查询企业权限。
     * pid ~ 父 id。
     *
     * @param map
     * @return
     */
    @Override
    public List<Permission> findAll(Map<String, Object> map) {
        // 需要查询条件。
        //    参数：map。
        Specification<Permission> specification = new Specification<Permission>() {
            /**
             * 动态拼接查询条件。
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 根据请求的父 Id 是否为空构造查询条件。
                if (!StringUtils.isEmpty(map.get("pid"))) {
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class), map.get("pid")));
                }
                // 根据请求的 enVisible 是否为空构造查询条件。
                if (!StringUtils.isEmpty(map.get("enVisible"))) {
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class), map.get("enVisible")));
                }
                // 根据 type。
                if (!StringUtils.isEmpty(map.get("type"))) {
                    // 根据请求的 hasDept 判断。是否分配部门。0 未分配（departmentId == null）。1 已分配（departmentId != null）
                    if (!StringUtils.isEmpty(map.get("type"))) {
                        String type = (String) map.get("type");

                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));

                        if ("0".equals(type)) {
                            // 需要比较的值。
                            in.value(1).value(2);
                        } else {
                            in.value(Integer.parseInt(type));
                        }
                    } else {
                        list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(specification);
    }

    /**
     * 根据 id 删除权限。
     *
     * @param id
     * @throws CommonException
     */
    @Override
    public void deleteById(String id) throws CommonException {
        // 通过传递的权限 id 查询权限。
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);
        // 根据类型构造不同的资源对象。（菜单、按钮、api）。
        Integer type = permission.getType();
        switch (type) {
            case PermissionConstants
                    .PERMISSION_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants
                    .PERMISSION_POINT:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants
                    .PERMISSION_API:
                permissionMenuDao.deleteById(id);
                break;

            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }

    /**
     * 根据 id 查询权限。
     *
     * @param id
     * @return
     * @throws CommonException
     */
    @Override
    public Map<String, Object> findById(String id) throws CommonException {
        Permission permission = permissionDao.findById(id).get();
        Integer type = permission.getType();

        Object object = null;

        if (type == PermissionConstants.PERMISSION_MENU) {
            object = permissionMenuDao.findById(id).get();
        } else if (type == PermissionConstants.PERMISSION_POINT) {
            object = permissionMenuDao.findById(id).get();
        } else if (type == PermissionConstants.PERMISSION_API) {
            object = permissionMenuDao.findById(id).get();
        } else {
            throw new CommonException(ResultCode.FAIL);
        }

        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name", permission.getName());
        map.put("type", permission.getType());
        map.put("code", permission.getCode());
        map.put("description", permission.getDescription());
        map.put("pid", permission.getPid());
        map.put("enVisible", permission.getEnVisible());

        return map;
    }
}
