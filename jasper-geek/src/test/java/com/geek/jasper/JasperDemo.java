package com.geek.jasper;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class JasperDemo {

    public static void main(String[] args) {
        createJasper();
        createJrprint();
        showPdf();
    }

    // 设计阶段。design。
    // 所谓的报表设计就是创建一些模板，模板包含了报表的布局和设计，包括执行计算的复杂公式、可选的从数据源获取数据的查询语句、以及其他的一些信息。模板设计完成之后。我们将模板保存为 .jrxml 文件（JasperReports），其实就是一个 xml 文件。
    // 将 pdf 模板编译为 Jasper 文件。
    public static void createJasper() {
        try {
            String path = "./test01.jrxml";
            String s = JasperCompileManager.compileReportToFile(path);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    // 执行阶段。Execution。
    // 使用以 JEXML 文件编译为可执行二进制文件（.jasper）结合数据进行执行，填充报表数据。得到 Jrprint。
    public static void createJrprint() {
        try {
            String path = "./test01.jasper";
            // 通过空参数的空数据源进行填充。
            String s = JasperFillManager.fillReportToFile(path, new HashMap<>(), new JREmptyDataSource());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    // 输出阶段。Export。
    // 数据填充结束，可以指定输出位多种形式的报表。
    public static void showPdf() {
        try {
            String path = "./test01.jrprint";
            JasperViewer.viewReport(path, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
