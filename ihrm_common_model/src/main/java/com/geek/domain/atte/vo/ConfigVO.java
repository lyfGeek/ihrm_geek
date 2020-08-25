package com.geek.domain.atte.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ConfigVO implements Serializable {

    /**
     * 公司 ID。
     */
    private String companyId;

    /**
     * 部门 ID。
     */
    @NotBlank(message = "部门 ID 不能为空。")
    private String departmentId;
}
