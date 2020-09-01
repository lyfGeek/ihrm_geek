package com.geek.domain.atte.entity;

import com.geek.domain.atte.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 考勤配置表。
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "atte_attendance_config")
@Data
public class AttendanceConfig extends BaseEntity implements Serializable {

    @Id
    private String id;

    private String companyId;

    @NotBlank(message = "部门 ID 不能为空。")
    private String departmentId;

    @NotNull(message = "上午上班时间不能为空。")
    private String morningStartTime;
    @NotNull(message = "上午下班时间不能为空。")
    private String morningEndTime;
    @NotNull(message = "下午上班时间不能为空。")
    private String afternoonStartTime;
    @NotNull(message = "下午下班时间不能为空。")
    private String afternoonEndTime;
}
