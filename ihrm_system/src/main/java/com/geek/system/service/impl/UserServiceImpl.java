package com.geek.system.service.impl;

import com.baidu.aip.util.Base64Util;
import com.geek.common.utils.IdWorker;
import com.geek.common.utils.QiniuUploadUtil;
import com.geek.domain.company.Department;
import com.geek.domain.system.Role;
import com.geek.domain.system.User;
import com.geek.system.client.IDepartmentFeignClient;
import com.geek.system.dao.IRoleDao;
import com.geek.system.dao.IUserDao;
import com.geek.system.service.IUserService;
import com.geek.system.utils.BaiduAiUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IDepartmentFeignClient departmentFeignClient;

    @Autowired
    private BaiduAiUtil baiduAiUtil;

    /**
     * 批量保存用户。
     *
     * @param list
     * @param companyId
     * @param companyName
     */
    @Override
    @Transactional
    public void saveAll(List<User> list, String companyId, String companyName) {
        for (User user : list) {
            // 默认密码。
            user.setPassword(new Md5Hash("123456", user.getMobile(), 3).toString());
            // id。
            user.setId(idWorker.nextId() + "");
            // 基本属性。
            user.setCompanyId(companyId);
            user.setCompanyName(companyName);
            user.setInServiceStatus(1);
            user.setEnableState(1);
            user.setLevel("user");

            // 补充部门属性。
            Department department = departmentFeignClient.findByCode(user.getDepartmentId(), companyId);
            if (department != null) {
                user.setDepartmentId(department.getId());
                user.setDepartmentName(department.getName());
            }

            userDao.save(user);
        }
    }

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

    /**
     * 上传图片并返回图片路径。七牛云。
     * 并注册到百度云 AI 人脸库中。
     * <p>
     * - 调用百度云接口，判断当前用户是否已经注册。
     * 已注册，更新。
     * 未注册，注册。
     *
     * @param id
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @Override
    public String uploadImage(String id, MultipartFile multipartFile) throws IOException {
        // 根据 id 查询用户。
        User user = userDao.findById(id).get();

        // 将图片上传到七牛云，获取路径。
        String imgUrl = new QiniuUploadUtil().upload(user.getId(), multipartFile.getBytes());

        // 更新用户头像地址。
        user.setStaffPhoto(imgUrl);
        userDao.save(user);

        // 借助 baiduAiUtil 判断是否注册。
        Boolean faceExist = baiduAiUtil.faceExist(id);

        String imgBase64 = Base64Util.encode(multipartFile.getBytes());

        if (faceExist) {
            // 已经存在，更新。
            baiduAiUtil.faceUpdate(id, imgBase64);
        } else {
            // 不存在，注册。
            baiduAiUtil.faceRegister(id, imgBase64);
        }
        // 返回。
        return imgUrl;
    }

    /**
     * 上传图片并返回图片路径。DataUrl。
     *
     * @param id            用户 id。
     * @param multipartFile 用户上传的头像文件。
     * @return 请求路径。
     */
//    @Override
//    public String uploadImage(String id, MultipartFile multipartFile) throws IOException {
//        // 根据 id 查询用户。
//        User user = userDao.findById(id).get();
//        // 使用 dataUrl 形式存储图片。对图片 byte 数组进行 BASE64 编码。
//        String encode = "data:image/png;base64," + new String(Base64.getEncoder().encode(multipartFile.getBytes()));
//        System.out.println("encode = " + encode);
//        // 更新用户头像地址。
//        user.setStaffPhoto(encode);
//        userDao.save(user);
//        // 返回。
//        return encode;
//    }
}
