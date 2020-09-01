package com.geek.freemarker.test;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试字符串模板。
 */
public class FreeMarkerTest02 {

    @Test
    public void test() throws IOException, TemplateException {
        // 创建配置对象。
        Configuration configuration = new Configuration();
        // 指定加载器。
        configuration.setTemplateLoader(new StringTemplateLoader());
        // 创建字符串模板。
        String templateString = "欢迎您：${username}";
        Template template = new Template("name1", new StringReader(templateString), configuration);
        // 构造数据。
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("username", "李");
        // 处理模板。
        template.process(dataModel, new PrintWriter(System.out));
    }
}
