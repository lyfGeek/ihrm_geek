package com.geek.domain.atte.entity;

import com.geek.domain.atte.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 调休配置表
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "atte_day_off_config")
@Data
public class DayOffConfig extends BaseEntity implements Serializable {

    @Id
    private String id;
    private String companyId;

    /**
     * 部门 ID。
     */
    private String departmentId;
    /**
     * 调休最后有效日期。
     */
    private String latestEffectDate;
    /**
     * 调休单位。
     */
    private String unit;
}
