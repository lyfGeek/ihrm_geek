package com.geek.generate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 需要将自定义的配置信息写入到 properties 文件中。
 * 配置到相对于工程的 properties 文件夹下。
 */
public class PropertiesUtils {

    // 自定义的数据模式 map 集合。
    public static Map<String, String> customMap = new HashMap<>();

    static {
        File dir = new File("codeutils/properties");
        try {
            List<File> files = FileUtils.searchAllFile(new File(dir.getAbsolutePath()));
            System.out.println("files = " + files);
            for (File file : files) {
                if (file.getName().endsWith(".properties")) {
                    Properties properties = new Properties();
                    properties.load(new FileInputStream(file));
                    customMap.putAll((Map) properties);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PropertiesUtils.customMap.forEach((k, v) -> System.out.println(k + " ~ ~ ~ " + v));
    }
}
