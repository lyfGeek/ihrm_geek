package com.geek.domain.atte.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AtteSalaryStatisticsVO implements Serializable {

    /**
     * 用户 ID。
     */
    @NotBlank(message = "用户 ID 不能为空。")
    private String userId;

    /**
     * 每次扣款薪资。
     */
    @NotNull(message = "每次扣款薪资不能为空。")
    private String dedSalaryPerTimes;

    /**
     * 考勤日期。
     */
    @NotBlank(message = "考勤日期不能为空，格式yyyy-mm。")
    private String atteDate;

    /**
     * 部门 ID。
     */
    @NotBlank(message = "部门 ID 不能为空。")
    private String departmentId;

    /**
     * 公司 ID。
     */
    private String companyId;
}
