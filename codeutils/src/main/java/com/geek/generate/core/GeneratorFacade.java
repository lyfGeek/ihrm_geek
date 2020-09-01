package com.geek.generate.core;

import com.geek.generate.entity.DataBase;
import com.geek.generate.entity.Settings;
import com.geek.generate.entity.Table;
import com.geek.generate.utils.DataBaseUtils;
import com.geek.generate.utils.PropertiesUtils;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * - 采集用户 UI 界面输入的数据。
 * 模板位置。
 * 生成代码路径。
 * 工程配置对象 Settings。
 * 数据库对象 DataBase。
 * <p>
 * - 准备数据模型。
 * 自定义配置。
 * 元数据。
 * Settings。
 * <p>
 * - 调用核心处理类完成代码生成工作。
 * Generator。
 * <p>
 * 程序的入口。
 */
public class GeneratorFacade {

    // facade
    // n. （建筑物的）正面，立面；（虚假的）表面，外表

    private String templatePath;
    private String outPath;
    private Settings settings;
    private DataBase dataBase;

    private Generator generator;

    public GeneratorFacade(String templatePath, String outPath, Settings settings, DataBase dataBase) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        this.settings = settings;
        this.dataBase = dataBase;
        this.generator = new Generator(templatePath, outPath);
    }

    /**
     * 准备数据模型。
     * 调用核心处理类 Generator 完成代码生成工作。
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void generateByDatabase() throws SQLException, ClassNotFoundException, IOException, TemplateException {
        List<Table> tables = DataBaseUtils.getDatabaseInfo(dataBase);
        for (Table table : tables) {
            // 对每一个 Table 对象进行代码生成。
            Map<String, Object> dataModel = getDataModel(table);

//            for (Map.Entry<String, Object> entry : dataModel.entrySet()) {
//                System.out.println(entry.getKey() + " ~ ~ ~ " + entry.getValue());
//            }

            generator.scanAndGenerate(dataModel);
        }
    }

    /**
     * 根据 table 对象获取数据模型。
     *
     * @return
     */
    private Map<String, Object> getDataModel(Table table) {
        Map<String, Object> dataModel = new HashMap<>();
        // 自定义配置。
        dataModel.putAll(PropertiesUtils.customMap);
        // 元数据。
        dataModel.put("table", table);
        // setting。
        dataModel.putAll(this.settings.getSettingMap());
        // 类型。
        dataModel.put("ClassName", table.getName2());
        return dataModel;
    }
}
