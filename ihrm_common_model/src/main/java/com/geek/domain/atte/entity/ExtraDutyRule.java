package com.geek.domain.atte.entity;

import com.geek.domain.atte.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 加班规则。
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "atte_extra_duty_rule")
@Data
public class ExtraDutyRule extends BaseEntity implements Serializable {

    @Id
    private String id;
    private String extraDutyConfigId;
    private String companyId;
    private String departmentId;

    /**
     * 规则内容。
     */
    private String rule;
    private String ruleStartTime;
    private String ruleEndTime;
    /**
     * 是否调休。0 ~ 不调休。1 ~ 调休。
     */
    private Integer isTimeOff;
    /**
     * 是否可用。0 ~ 开启。1 ~ 关闭。
     */
    private Integer isEnable;
}
