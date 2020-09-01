package com.geek.generate.entity;

import lombok.Data;

/**
 * 列对象。
 */
@Data
public class Column {

    private String columnName;// 列名称。
    private String columnName2;// 处理后的列名称。
    private String columnType;// Java 类型。
    private String columnDbType;// 数据库列类型。
    private String columnComment;// 列备注。
    private String columnKey;// 是否是主键。
}
