package com.geek.generate.entity;

import lombok.Data;

/**
 * 数据库实体类。
 */
@Data
public class DataBase {

    private static String musqlUrl = "jdbc:mysql://[ip]:[port]/[db]?useUnicode=true&amp;characterEncoding=UTF8";
    private static String oracleUrl = "jdbc:oracle:thin:@[ip]:[port]:[db]";

    private String dbType;// 数据库类型。
    private String driver;
    private String url;
    private String userName;
    private String passWord;

    public DataBase() {
    }

    public DataBase(String dbType) {
        this(dbType, "192.168.142.161", "3007", "");
    }

    public DataBase(String dbType, String db) {
        this(dbType, "192.168.142.161", "3307", db);
    }

    /**
     * @param dbType 数据库类型。
     * @param ip     ip。
     * @param port   3306。
     * @param db     ihrm。
     */
    public DataBase(String dbType, String ip, String port, String db) {
        this.dbType = dbType;
        if ("MYSQL".endsWith(dbType.toUpperCase())) {
            this.driver = "com.mysql.jdbc.Driver";
            this.url = musqlUrl.replace("[ip]", ip).replace("[port]", port).replace("[db]", db);
        } else {
            {
                this.driver = "oracle.jdbc.driver.OracleDriver";
                this.url = oracleUrl.replace("[ip]", ip).replace("[port]", port).replace("[db]", db);
            }
        }
    }
}
