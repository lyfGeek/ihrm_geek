package com.geek.domain.system;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bs_city")
@Data
public class City implements Serializable {

    @Id
    private String id;
    private String name;
    private Date createTime;
}
