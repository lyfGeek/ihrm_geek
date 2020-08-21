package com.geek.shiro.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pe_permission")
@Getter
@Setter
@NoArgsConstructor
public class Permission implements Serializable {
    /**
     * 主键。
     */
    @Id
    private String id;
    private String name;
    private String code;
    private String description;
}
