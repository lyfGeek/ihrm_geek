package com.geek.system.service.impl;

import com.geek.common.utils.IdWorker;
import com.geek.domain.system.Role;
import com.geek.domain.system.User;
import com.geek.system.dao.IRoleDao;
import com.geek.system.dao.IUserDao;
import com.geek.system.service.IUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IRoleDao roleDao;

    /**
     * 根据 mobile 查询用户。
     *
     * @param mobile
     * @return
     */
    @Override
    public User findByMobile(String mobile) {
        return userDao.findByMobile(mobile);
    }

    /**
     * 保存用户。
     *
     * @param user
     */
    @Override
    public void save(User user) {
        // 设置主键的值。
        String id = idWorker.nextId() + "";
        // 加密密码。
        String password = new Md5Hash("123456", user.getMobile(), 3).toString();// 密码，盐，加密次数。
        user.setPassword(password);// 设置初始密码。
        user.setLevel("user");// 用户等级。
        user.setEnableState(1);
        user.setId(id);
        // 保存。
        userDao.save(user);
    }

    /**
     * 更新用户。
     *
     * @param user
     */
    @Override
    public void update(User user) {
        // 根据 id 查询用户。
        Optional<User> optional = userDao.findById(user.getId());
        User target = optional.orElse(null);
        // 设置用户属性。
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setDepartmentId(user.getDepartmentId());
        target.setDepartmentName(user.getDepartmentName());
        userDao.save(target);
    }

    /**
     * 查询全部用户列表。
     *
     * @param map
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page findAll(Map<String, Object> map, int page, int size) {
        // 需要查询条件。
        //    参数：map。
        //      hasDept
        //      departmentId
        //      companyId
        Specification<User> specification = new Specification<User>() {
            /**
             * 动态拼接查询条件。
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 根据请求的 companyId 是否为空构造查询条件。
                if (!StringUtils.isEmpty(map.get("companyId"))) {
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), map.get("companyId")));
                }
                // 根据请求的部门 ID 是否为空构造查询条件。
                if (!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), map.get("departmentId")));
                }
                if (!StringUtils.isEmpty(map.get("hasDept"))) {

                    // 根据请求的 hasDept 判断。是否分配部门。0 未分配（departmentId == null）。1 已分配（departmentId != null）
                    if ("0".equals(map.get("hasDept"))) {
                        list.add(criteriaBuilder.isNull(root.get("departmentId")));
                    } else {
                        list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        // 分页。
        Page<User> userPage = userDao.findAll(specification, new PageRequest(page - 1, size));
        return userPage;
    }

    /**
     * 根据 id 删除用户。
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 根据 id 查询用户。
     *
     * @param id
     */
    @Override
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 分配角色。
     *
     * @param userId
     * @param roleIds
     */
    @Override
    public void assignRoles(String userId, List<String> roleIds) {
        // 根据 id 查询用户。
        User user = userDao.findById(userId).get();
        // 设置用户的角色集合。
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        // 设置用户和角色集合关系。
        user.setRoles(roles);
        // 更新用户。
        userDao.save(user);
    }
}
