package com.geek.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 转正申请实体类。
 */
@Entity
@Table(name = "em_positive")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePositive implements Serializable {
    /**
     * 员工 ID。
     */
    @Id
    private String userId;
    /**
     * 转正日期。
     */
    private Date dateOfCorrection;
    /**
     * 转正评价。
     */
    private String correctionEvaluation;
    /**
     * 附件。
     */
    private String enclosure;
    /**
     * 单据状态。1 ~ 未执行。2 ~ 已执行。
     */
    private Integer estatus;
    /**
     * 创建时间。
     */
    private Date createTime;
}
