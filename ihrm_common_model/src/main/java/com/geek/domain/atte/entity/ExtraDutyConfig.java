package com.geek.domain.atte.entity;

import com.geek.domain.atte.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 加班配置表。
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "atte_extra_duty_config")
@Data
public class ExtraDutyConfig extends BaseEntity implements Serializable {

    @Id
    private String id;
    private String companyId;
    private String departmentId;

    /**
     * 每日标准工作时长，单位小时。
     */
    private String workHoursDay;
    /**
     * 是否打卡。0 ~ 开启。1 ~ 关闭。
     */
    private Integer isClock;
    /**
     * 是否开启加班补偿。0 ~ 开启。1 ~ 关闭。
     */
    private String isCompensationint;
}
