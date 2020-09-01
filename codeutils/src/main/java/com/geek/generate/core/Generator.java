package com.geek.generate.core;

import com.geek.generate.utils.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器核心处理类。
 * Freemarker 完成文件生成。
 * 数据模型 + 模板。
 * <p>
 * - 数据：
 * 数据模型。
 * 模板的位置。
 * 生成文件的路径。
 */
public class Generator {

    private String templatePath;// 模板路径。
    private String outPath;// 代码生成路径。

    private Configuration configuration;

    public Generator(String templatePath, String outPath) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        // 实例化 Configuration 对象。
        configuration = new Configuration();
        // 指定模板加载器。
        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(templatePath));
        configuration.setTemplateLoader(fileTemplateLoader);
    }

    public static void main(String[] args) throws IOException, TemplateException {
        String templatePath = "D:\\lyfGeek。\\geek_my。\\geek_my。\\测试\\模板";
        String outPath = "D:\\lyfGeek。\\geek_my。\\geek_my。\\测试\\生成代码路径";
        Generator generator = new Generator(templatePath, outPath);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("username", "张三");
        generator.scanAndGenerate(dataModel);
    }

    /**
     * 代码生成。
     * <p>
     * 扫描模板路径下的所有模板。
     * 对每个模板进行文件生成。（数据模型。）
     *
     * @param dataModel
     */
    public void scanAndGenerate(Map<String, Object> dataModel) throws IOException, TemplateException {
        // 扫描模板路径下的所有模板。
        // 根据模板路径找到此路径下的所有模板文件。
        List<File> fileList = FileUtils.searchAllFile(new File(templatePath));
        // 对每个模板进行文件生成。
        for (File file : fileList) {
            executeGenerate(dataModel, file);
        }
    }

    /**
     * 对模板进行文件生成。
     *
     * @param dataModel 数据模型。
     * @param file      模板文件。
     */
    private void executeGenerate(Map<String, Object> dataModel, File file) throws IOException, TemplateException {
        // 文件路径处理。
        // （E:\模板\com\geek\ihrm\User.java）。
        // （E:\模板\${path1}\${path1}\${path1}\${ClassName}.java）。
        // templatePath：E:\模板\
        String templateFileName = file.getAbsolutePath().replace(this.templatePath, "");
        String outFileName = processTemplateString(templateFileName, dataModel);
        // 读取文件模板。
        Template template = configuration.getTemplate(templateFileName);
        template.setOutputEncoding("utf-8");// 指定生成文件的字符集编码 。

        // 创建新的文件。
        File file1 = FileUtils.mkdir(outPath, outFileName);

        // 模板处理，文件生成。
        FileWriter fileWriter = new FileWriter(file1);
        template.process(dataModel, fileWriter);
        fileWriter.close();
    }

    private String processTemplateString(String templateString, Map dataModel) throws IOException, TemplateException {

        StringWriter stringWriter = new StringWriter();
        Template template = new Template("ts", new StringReader(templateString), configuration);
        template.process(dataModel, stringWriter);
        return stringWriter.toString();
    }
}
