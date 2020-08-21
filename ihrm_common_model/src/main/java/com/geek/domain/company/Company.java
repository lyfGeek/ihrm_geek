package com.geek.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "co_company")
public class Company {

    @Id
    private String id;
    @Column(name = "name")
    private String name;
    private String managerId;
    private String version;
    private Date renewalDate;
    private Date expirationDate;
    private String companyArea;
    private String companyAddress;
    private String businessLicenseId;
    private String legalRepresentative;
    private String companyPhone;
    private String mailbox;
    private String companySize;
    private String industry;
    private String remarks;
    private String auditState;
    private Integer state;
    private Double balance;
    private Date create_time;
}


/*
CREATE TABLE `ihrm`.`co_company` (
  `id` VARCHAR(40) NOT NULL COMMENT 'ID。',
  `name` VARCHAR(255) NOT NULL COMMENT '公司名称。',
  `manager_id` VARCHAR(255) NOT NULL COMMENT '企业登录账号 id。',
  `version` VARCHAR(255) NULL DEFAULT NULL COMMENT '当前版本。',
  `renewal_date` DATETIME NULL DEFAULT NULL COMMENT '续期时间。',
  `expiration_date` DATETIME NULL DEFAULT NULL COMMENT '到期时间。',
  `company_area` VARCHAR(255) NULL DEFAULT NULL COMMENT '公司地区。',
  `company_address` TEXT NULL COMMENT '公司地址。',
  `business_license_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '营业执照-图片 id。',
  `legal_representative` VARCHAR(255) NULL DEFAULT NULL COMMENT '法人代表。',
  `company_phone` VARCHAR(255) NULL DEFAULT NULL COMMENT '公司电话。',
  `mailbox` VARCHAR(255) NULL DEFAULT NULL COMMENT '邮箱。',
  `company_size` VARCHAR(255) NULL DEFAULT NULL COMMENT '公司规模。',
  `industry` VARCHAR(255) NULL DEFAULT NULL COMMENT '所属行业。',
  `remarks` TEXT NULL COMMENT '备注。',
  `audit_state` VARCHAR(255) NULL DEFAULT NULL COMMENT '审核状态。',
  `state` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '状态。',
  `balance` DOUBLE NOT NULL COMMENT '当前余额。',
  `create_time` DATETIME NULL COMMENT '创建时间。')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


 */
