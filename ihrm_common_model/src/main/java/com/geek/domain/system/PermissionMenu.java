package com.geek.domain.system;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 菜单权限实体类。
 */
@Entity
@Table(name = "pe_permission_menu")
@Data
public class PermissionMenu implements Serializable {
    /**
     * 主键。
     */
    @Id
    private String id;

    /**
     * 展示图标。
     */
    private String menuIcon;

    /**
     * 排序号。
     */
    private String menuOrder;
}
