package com.geek.domain.social_security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ss_company_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySettings implements Serializable {
    /**
     * 企业 id。
     */
    @Id
    private String companyId;
    /**
     * 是否设置。0 为未设置，1 为已设置。
     */
    private Integer isSettings;
    /**
     * 当前显示报表月份。
     */
    private String dataMonth;
}