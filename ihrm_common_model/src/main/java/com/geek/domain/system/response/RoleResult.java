package com.geek.domain.system.response;

import com.geek.domain.system.Permission;
import com.geek.domain.system.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class RoleResult implements Serializable {

    private String id;
    /**
     * 角色名。
     */
    private String name;
    /**
     * 说明。
     */
    private String description;
    /**
     * 企业 id。
     */
    private String companyId;

    private List<String> PermIds = new ArrayList<>();

    public RoleResult(Role role) {
        BeanUtils.copyProperties(role, this);

        Set<Permission> permissions = role.getPermissions();
        for (Permission permission : permissions) {
            this.PermIds.add(permission.getId());
        }

//        user.getRoles().forEach(role -> this.roleIds.add(role.getId()));
    }
}
