package com.geek.generate.entity;

import com.geek.generate.utils.StringUtils;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
public class Settings {

    private String project = "example";
    private String pPackage = "com.geek.demo";
    private String projectComment;
    private String author;
    private String path1 = "com";
    private String path2 = "geek";
    private String path3 = "demo";
    private String pathAll;

    public Settings(String project, String pPackage, String projectComment, String author) {
        if (StringUtils.isNotBlank(project)) {
            this.project = project;
        }
        if (StringUtils.isNotBlank(pPackage)) {
            this.pPackage = pPackage;
        }
        this.projectComment = projectComment;
        this.author = author;

        String[] paths = pPackage.split("\\.");
        path1 = paths[0];
        path2 = paths.length > 1 ? paths[1] : path2;
        path3 = paths.length > 2 ? paths[2] : path3;
        pathAll = pPackage.replaceAll(".", "/");
    }

    public Map<String, Object> getSettingMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = Settings.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                map.put(declaredField.getName(), declaredField.get(this));
            } catch (Exception e) {
            }
        }
        return map;
    }
}
