package com.geek.metadata.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * 测试参数元数据。
 */
public class ParameterMetaDataTest {

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

        // 获取元数据。
        ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();

        System.out.println(parameterMetaData);
        System.out.println(parameterMetaData.getParameterCount());

        boolean execute = preparedStatement.execute();
        System.out.println(execute);

        ResultSet resultSet = preparedStatement.executeQuery();

        // 获取查询字段数量。
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        System.out.println("columnCount = " + columnCount);

        for (int i = 1; i <= columnCount; i++) {
            // 获取表名称。
            String catalogName = metaData.getCatalogName(i);
            System.out.println("catalogName = " + catalogName);
            // 获取 Java 类型。
            String columnClassName = metaData.getColumnClassName(i);
            System.out.println("columnClassName = " + columnClassName);
            // 获取 sql 类型。
            String columnTypeName = metaData.getColumnTypeName(i);
            System.out.println("columnTypeName = " + columnTypeName);
        }
    }
}
