//package com.geek.system.service.impl;
//
//import com.geek.common.service.BaseService;
//import com.geek.domain.system.Role;
//import com.geek.domain.system.RoleAndUserRelations;
//import com.geek.system.dao.IUserAndRoleRelationsDao;
//import com.geek.system.service.IRoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserAndRoleRelationsServiceImpl extends BaseService {
//
//    @Autowired
//    private IUserAndRoleRelationsDao userAndRoleRelationsDao;
//
//    @Autowired
//    private IRoleService roleService;
//
//    public List<RoleAndUserRelations> findRoleByUserId(String userId) {
//        return userAndRoleRelationsDao.findByUserId(userId);
//    }
//
//    public List<Role> getRoleDetailByRoleId(List<RoleAndUserRelations> roleByUserId) {
//        List<Role> res = new ArrayList<>();
//        for (RoleAndUserRelations userAndRoleRea : roleByUserId) {
//            Role role = roleService.findById(userAndRoleRea.getRoleId());
//            if (!ObjectUtils.isEmpty(role)) {
//                res.add(role);
//            }
//        }
//        return res;
//    }
//}
