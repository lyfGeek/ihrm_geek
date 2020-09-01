package com.geek.domain.system;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 按钮资源。
 */
@Entity
@Table(name = "pe_permission_point")
@Data
public class PermissionPoint implements Serializable {
    /**
     * 主键。
     */
    @Id
    private String id;

    /**
     * 权限代码。
     */
    private String pointClass;

    private String pointIcon;

    private String pointStatus;
}
