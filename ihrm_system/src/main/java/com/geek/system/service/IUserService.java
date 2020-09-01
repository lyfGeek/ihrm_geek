package com.geek.system.service;

import com.geek.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IUserService {

    User findByMobile(String mobile);

    /**
     * 批量保存用户。
     *
     * @param list
     * @param companyId
     * @param companyName
     */
    void saveAll(List<User> list, String companyId, String companyName);

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

    /**
     * 上传图片并返回图片路径。
     *
     * @param id
     * @param multipartFile
     * @return
     */
    String uploadImage(String id, MultipartFile multipartFile) throws IOException;
}
