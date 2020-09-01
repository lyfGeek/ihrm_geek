package com.geek.metadata.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * 测试数据库元数据。
 */
public class DatabaseMetaDataTest {

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

    // 获取数据库基本信息。
    @Test
    public void test00() throws SQLException {

        // 获取元数据。
        DatabaseMetaData metaData = connection.getMetaData();

        // 获取数据库基本信息。
        System.out.println(metaData.getUserName());
        boolean supportsTransactions = metaData.supportsTransactions();// 是否支持事务。
        System.out.println("supportsTransactions = " + supportsTransactions);
        String databaseProductName = metaData.getDatabaseProductName();
        System.out.println("databaseProductName = " + databaseProductName);

        connection.close();
    }

    // 获取数据库列表。
    @Test
    public void test01() throws SQLException {

        // 获取元数据。
        DatabaseMetaData metaData = connection.getMetaData();

        // 获取所有数据库列表。
        ResultSet resultSet = metaData.getCatalogs();
        while (resultSet.next()) {
            System.out.println(resultSet);
            System.out.println(resultSet.getString(1));// 数据库名称。
        }
        resultSet.close();
    }

    // 获取指定数据库中的表信息。
    @Test
    public void test02() throws SQLException {

        // 获取元数据。
        DatabaseMetaData metaData = connection.getMetaData();

        // 获取数据库中表信息。
        /**
         *     ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String types[]) throws SQLException;
         *
         * - cataLog ~ 当前操作的数据库。
         *      MySQL ~ :ihrm。
         *                  String url = "jdbc:mysql://192.168.142.161:3307/ihrm?characterEncoding=utf8";
         *                  地址写了库名称，就可以写 null。
         *      Oracle ~ xxx:1521:orcl。
         *
         * - schemaPattern。
         *      MySQL ~ null。
         *      Oracle ~ 用户名（大写）。
         *
         * - tableNamePattern。
         *      null ~ 查询所有表。
         *      非空 ~ 查询目标表。
         *
         * - types。
         *      TABLE ~ 表。
         *      VIEW ~ 视图。
         */
        ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

        while (resultSet.next()) {
            System.out.println(resultSet);
            System.out.println(resultSet.getString("TABLE_NAME"));
        }
    }

    // 获取指定表中的字段信息。
    @Test
    public void test03() throws SQLException {

        // 获取元数据。
        DatabaseMetaData metaData = connection.getMetaData();

        // 获取表中的字段信息。
        /**
         *     ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException;
         */
        ResultSet resultSet = metaData.getColumns(null, null, "bs_user", null);// null ~ 所有字段。
        while (resultSet.next()) {
            System.out.println(resultSet);
            System.out.println(resultSet.getString("COLUMN_NAME"));
        }
    }
}
