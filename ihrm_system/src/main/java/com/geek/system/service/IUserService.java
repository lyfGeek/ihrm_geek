package com.geek.system.service;

import com.geek.domain.system.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IUserService {

    User findByMobile(String mobile);

    /**
     * 保存用户。
     *
     * @param user
     */
    void save(User user);

    /**
     * 更新用户。
     *
     * @param user
     */
    void update(User user);

    /**
     * 查询全部用户列表。
     *
     * @return
     */
    Page findAll(Map<String, Object> map, int page, int size);

    /**
     * 根据 id 删除用户。
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据 id 查询用户。
     *
     * @param id
     */
    User findById(String id);

    /**
     * 分配角色。
     *
     * @param userId
     * @param roleIds
     */
    void assignRoles(String userId, List<String> roleIds);
}
