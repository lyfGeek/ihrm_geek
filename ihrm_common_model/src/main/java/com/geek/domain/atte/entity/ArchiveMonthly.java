package com.geek.domain.atte.entity;

import com.geek.domain.atte.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "atte_archive_monthly")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ArchiveMonthly extends BaseEntity implements Serializable {

    @Id
    private String id;
    private String companyId;
    private String departmentId;

    private String archiveYear;
    private String archiveMonth;
    private Integer totalPeopleNum;

    private Integer fullAttePeopleNum;
    private Integer isArchived;
}
