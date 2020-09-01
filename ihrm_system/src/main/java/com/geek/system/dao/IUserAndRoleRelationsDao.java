//package com.geek.system.dao;
//
//import com.geek.domain.system.RoleAndUserRelations;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//import java.util.List;
//
//public interface IUserAndRoleRelationsDao extends JpaRepository<RoleAndUserRelations, String>, JpaSpecificationExecutor<RoleAndUserRelations> {
//
//    /**
//     * 根据用户 id 查询对应的角色 id。
//     *
//     * @param userId
//     * @return
//     */
//    List<RoleAndUserRelations> findByUserId(String userId);
//}
