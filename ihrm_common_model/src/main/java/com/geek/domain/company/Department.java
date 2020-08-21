package com.geek.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Department)实体类。
 */
@Entity
@Table(name = "co_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    // ID。
    @Id
    private String id;
    /**
     * 父级ID。
     */
    private String parentId;
    /**
     * 企业ID。
     */
    private String companyId;
    /**
     * 部门名称。
     */
    private String name;
    /**
     * 部门编码，同级部门不可重复。
     */
    private String code;
    /**
     * 负责人ID。
     */
    private String managerId;
    /**
     * 负责人名称。
     */
    private String manager;
    /**
     * 介绍。
     */
    private String introduce;
    /**
     * 创建时间。
     */
    private Date createTime;
}

/*
CREATE TABLE `ihrm`.`co_department` (
  `id` VARCHAR(40) NOT NULL,
  `company_id` VARCHAR(255) NOT NULL COMMENT '企业 id。',
  `parent_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '父级部门 id。',
  `name` VARCHAR(255) NOT NULL COMMENT '部门名称。',
  `code` VARCHAR(255) NOT NULL COMMENT '部门编码。',
  `category` VARCHAR(255) NULL DEFAULT NULL COMMENT '部门类别。',
  `manager_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '负责人 id。',
  `city` VARCHAR(255) NULL DEFAULT NULL COMMENT '城市。',
  `introduce` TEXT NULL COMMENT '介绍。',
  `create_time` DATETIME NOT NULL COMMENT '创建时间。',
  `manager` VARCHAR(40) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;
 */
