package com.geek.domain.atte.base;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    /**
     * 创建者。
     */
    private String createBy;
    /**
     * 创建日期。
     */
    private Date createDate;
    /**
     * 更新者。
     */
    private String updateBy;
    /**
     * 更新日期。
     */
    private Date updateDate;
    /**
     * 备注。
     */
    private String remarks;
}
