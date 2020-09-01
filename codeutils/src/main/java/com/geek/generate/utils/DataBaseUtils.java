package com.geek.generate.utils;

import com.geek.generate.entity.Column;
import com.geek.generate.entity.DataBase;
import com.geek.generate.entity.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataBaseUtils {

    // 获取 MySQL 中所有数据库的名称。

    // 获取数据库连接。
    public static Connection getConnection(DataBase dataBase) throws SQLException, ClassNotFoundException {

        Properties properties = new Properties();
        properties.put("remarksReporting", "true");// 获取数据库的备注信息。
        properties.put("user", dataBase.getUserName());
        properties.put("password", dataBase.getPassWord());

        // 获取连接。
        Class.forName(dataBase.getDriver());// 注册驱动。
        return DriverManager.getConnection(dataBase.getUrl(), properties);
    }

    // 获取数据库列表。
    public static List<String> getSchemas(DataBase dataBase) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection(dataBase);
        // 获取元数据。
        DatabaseMetaData metaData = connection.getMetaData();

        // 获取所有数据库列表。
        ResultSet resultSet = metaData.getCatalogs();
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        resultSet.close();
        connection.close();
        return list;
    }

    // 获取数据库中表和字段构造实体类。
    public static List<Table> getDatabaseInfo(DataBase dataBase) throws SQLException, ClassNotFoundException {
        // 获取连接。
        Connection connection = getConnection(dataBase);
        // 获取 DatabaseMetaData。
        DatabaseMetaData metaData = connection.getMetaData();
        // 获取当前数据库中的所有表信息。
        ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

        List<Table> list = new ArrayList<>();

        while (resultSet.next()) {
            // 获取每个表中的所有字段。
            Table table = new Table();

            // 表名称。
            String tableName = resultSet.getString("TABLE_NAME");// bs_user。
            // 类名。
            String className = removePrefix(tableName);
            // 描述。
            String remarks = resultSet.getString("REMARKS");
            // 主键。
            ResultSet primaryKsys = metaData.getPrimaryKeys(null, null, tableName);

            String keys = "";
            while (primaryKsys.next()) {
                String keyName = primaryKsys.getString("COLUMN_NAME");
                keys += keyName + ",";
            }
            table.setName(tableName);
            table.setName2(className);
            table.setComment(remarks);
            table.setKey(keys);

            // 处理表中的所有字段。
            // 封装到 Java 对象中。
            ResultSet columns = metaData.getColumns(null, null, tableName, null);// null ~ 所有字段。

            List<Column> columnList = new ArrayList<>();
            while (columns.next()) {
                Column column = new Column();

                // 构造 Column 对象。
                // 列名称。
                String columnName = columns.getString("COLUMN_NAME");// user_id -> userId, create_time -> createTime。
                column.setColumnName(columnName);

                // 属性名。
                String attName = StringUtils.toJavaVariableName(columnName);
                column.setColumnName2(attName);

                // Java 类型和数据库类型。
                String dbType = columns.getString("TYPE_NAME");// VARCHAR, DATETIME。
                column.setColumnDbType(dbType);
                String javaType = PropertiesUtils.customMap.get(dbType);
                column.setColumnType(javaType);

                // 备注。
                String columnRemark = columns.getString("REMARKS");
                column.setColumnComment(columnRemark);

                // 是否主键。
                String pri = null;
                if (StringUtils.contains(columnName, keys.split(","))) {
                    pri = "PRI";
                }
                column.setColumnKey(pri);

                columnList.add(column);
            }

            columns.close();

            table.setColumns(columnList);
            list.add(table);
        }

        resultSet.close();
        connection.close();

        return list;
    }

    private static String removePrefix(String tableName) {
        String prefixes = PropertiesUtils.customMap.get("tableRemovePrefixes");

        String temp = tableName;
        String[] prefixes1 = prefixes.split(",");
        for (String prefix : prefixes1) {
            temp = StringUtils.removePrefix(temp, prefix, true);
        }
        // temp -> user
        // 首字母大写。
        return StringUtils.makeAllWordFirstLetterUpperCase(temp);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBase dataBase = new DataBase("mysql", "ihrm");
        dataBase.setUserName("root");
        dataBase.setPassWord("root");
        List<Table> tableList = DataBaseUtils.getDatabaseInfo(dataBase);
        for (Table table : tableList) {
            System.out.println(table);
        }
    }
}
