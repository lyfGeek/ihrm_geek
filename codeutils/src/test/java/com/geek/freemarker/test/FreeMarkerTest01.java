package com.geek.freemarker.test;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkerTest01 {

    @Test
    public void test01() throws IOException, TemplateException {
        // 创建 FreeMarker 配置类。
        Configuration configuration = new Configuration();
        // 指定模板加载器，并将模板存入缓存中。
        // 文件路径加载器。
        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File("templates"));
        configuration.setTemplateLoader(fileTemplateLoader);
        // 获取模板。
        Template template = configuration.getTemplate("template01.ftl");
        // 构造数据模型。
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("username", "Geek");
        dataModel.put("number", 1);
        dataModel.put("flag", 2);

        List<String> list = new ArrayList<>();
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");

        dataModel.put("weeks", list);

        // 文件输出。
        /**
         * 参数 1： 数据模型。
         * 参数 2：writer(FileWriter（文件输出） / PrintWriter（控制台输出））。
         */
//        template.process(dataModel, new FileWriter(new File("./a.txt")));
        template.process(dataModel, new PrintWriter(System.out));
    }
}
