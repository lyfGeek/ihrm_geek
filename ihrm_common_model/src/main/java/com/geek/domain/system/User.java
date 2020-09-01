package com.geek.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geek.domain.poi.ExcelAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类。
 */
@Entity
@Table(name = "bs_user")
//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * ID。
     */
    @Id
    private String id;
    /**
     * 手机号码。
     */
    @ExcelAttribute(sort = 2)
    private String mobile;
    /**
     * 用户名称。
     */
    @ExcelAttribute(sort = 1)
    private String username;
    /**
     * 密码。
     */
    private String password;
    /**
     * 启用状态 0为禁用 1为启用。
     */
    private Integer enableState;
    /**
     * 创建时间。
     */
    private Date createTime;

    private String companyId;

    private String companyName;
    /**
     * 部门 ID。
     */
    @ExcelAttribute(sort = 6)
    private String departmentId;
    /**
     * 入职时间。
     */
    @ExcelAttribute(sort = 5)
    private Date timeOfEntry;
    /**
     * 聘用形式。
     */
    @ExcelAttribute(sort = 4)
    private Integer formOfEmployment;
    /**
     * 工号。
     */
    @ExcelAttribute(sort = 3)
    private String workNumber;
    /**
     * 管理形式。
     */
    private String formOfManagement;
    /**
     * 工作城市。
     */
    private String workingCity;
    /**
     * 转正时间。
     */
    private Date correctionTime;
    /**
     * 在职状态 1.在职 2.离职。
     */
    private Integer inServiceStatus;

    private String departmentName;

    /**
     * level。String。
     * saasAdmin ~ saas 管理员具备的有权限。
     * coAdmin ~ 企业管理（创建租户企业时添加）。
     * user ~ 普通用户（需要分配角色）。
     */
    private String level;

    /**
     * 用户头像。
     */
    private String staffPhoto;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "pe_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<>();// 用户与角色（多对多）。

    public User(Object[] values) {
        // 用户名	手机号	工号	聘用形式	入职时间	部门编码
        this.username = values[1].toString();
        this.mobile = values[2].toString();
        this.workNumber = new DecimalFormat("#").format(values[3]);
        this.formOfEmployment = ((Double) values[4]).intValue();
        this.timeOfEntry = (Date) values[5];
        this.departmentId = values[6].toString();// 部门编码不是部门 id。
    }
}

/*
CREATE TABLE `ihrm`.`bs_user` (
  `id` VARCHAR(40) NOT NULL COMMENT 'ID。',
  `mobile` VARCHAR(40) NOT NULL COMMENT '手机号码。',
  `username` VARCHAR(255) NOT NULL COMMENT '用户名称。',
  `password` VARCHAR(255) NULL DEFAULT NULL COMMENT '密码。',
  `enable_state` INT(2) NULL DEFAULT 1 COMMENT '启用状态。0 禁用。1 启用。',
  `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间。',
  `department_id` VARCHAR(40) NULL DEFAULT NULL COMMENT '部门 ID。',
  `time_of_entry` DATETIME NULL DEFAULT NULL COMMENT '入职时间。',
  `form_of_employment` INT(1) NULL DEFAULT NULL COMMENT '聘用形式。',
  `work_number` VARCHAR(20) NULL DEFAULT NULL COMMENT '工号。',
  `form_of_management` VARCHAR(8) NULL DEFAULT NULL COMMENT '管理形式。',
  `working_city` VARCHAR(16) NULL DEFAULT NULL COMMENT '工作城市。',
  `correction_time` DATETIME NULL DEFAULT NULL COMMENT '转正时间。',
  `in_service_status` INT(1) NULL DEFAULT NULL COMMENT '在职状态。1 在职。2 离职。',
  `company_id` VARCHAR(40) NULL DEFAULT NULL COMMENT '企业 ID。',
  `company_name` VARCHAR(40) NULL DEFAULT NULL COMMENT '企业名称。',
  `department_name` VARCHAR(40) NULL DEFAULT NULL COMMENT '部门名称。',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_user_phone` USING BTREE (`mobile`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

 */
