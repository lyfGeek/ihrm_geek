package com.geek.domain.social_security;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ss_archive")
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class Archive implements Serializable {

    @Id
    private String id;
    /**
     * 企业 id。
     */
    private String companyId;
    /**
     * 年月。
     */
    private String yearsMonth;
    /**
     * 创建时间。
     */
    private Date creationTime;
    /**
     * 企业缴费。
     */
    private BigDecimal enterprisePayment;
    /**
     * 个人缴费。
     */
    private BigDecimal personalPayment;
    /**
     * 合计。
     */
    private BigDecimal total;

    public Archive(String companyId, String yearMonth) {
        this.companyId = companyId;
        this.yearsMonth = yearMonth;
    }
}
