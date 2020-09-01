package com.geek.metadata.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * 测试结果集元数据。
 * 通过 ResultSet 获取。
 * 获取查询结果的信息。
 */
public class ResultSetMetaDataTest {

    private Connection connection;

    @Before
    public void init() throws SQLException {
        String driver = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://192.168.142.161:3307?characterEncoding=utf8";
        String url = "jdbc:mysql://192.168.142.161:3307/ihrm?characterEncoding=utf8";
        String username = "root";
        String password = "root";

        Properties properties = new Properties();
//        properties.put("remarksReporting", "true");// 获取数据库的备注信息。
        properties.put("user", username);
        properties.put("password", password);

        // 获取连接。
//        Class.forName(driver);// 注册驱动。

        connection = DriverManager.getConnection(url, properties);
    }

    @After
    public void after() throws SQLException {
        connection.close();
    }

    // SELECT
    //    *
    //FROM
    //    ihrm.bs_user
    //WHERE
    //    id = '1063705482939731968';
    @Test
    public void test00() throws SQLException {

        String sql = "SELECT * FROM ihrm.bs_user where id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, "1063705482939731968");

        boolean execute = preparedStatement.execute();
        System.out.println(execute);

        ResultSet resultSet = preparedStatement.executeQuery();

        // 获取结果集元数据。
        ResultSetMetaData metaData = resultSet.getMetaData();
        // 获取查询字段数量。
        int columnCount = metaData.getColumnCount();
        System.out.println("columnCount = " + columnCount);

        for (int i = 1; i < columnCount; i++) {
            // 获取列名。
            String columnName = metaData.getColumnName(i);
            System.out.println("columnName = " + columnName);
            // sql 字段类型。
            String columnTypeName = metaData.getColumnTypeName(i);
            System.out.println("columnTypeName = " + columnTypeName);
            // Java 类型。
            String columnClassName = metaData.getColumnClassName(i);
            System.out.println("columnClassName = " + columnClassName);
        }
    }
}
