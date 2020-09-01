package com.geek.controller;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

@RestController
public class JaperController {

    @GetMapping("/testJasper")
    public void createPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 引入 Jasper 文件。
        Resource resource = new ClassPathResource("templates/Blank_A4.jasper");
        FileInputStream fileInputStream = new FileInputStream(resource.getFile());

        ServletOutputStream responseOutputStream = response.getOutputStream();

        try {
            // 创建 JasperPrint。向 jasper 文件中填充数据。
            /**
             * fileInputStream ~ 文件输入流。
             * new HashMap ~ 向模板文件中输入的参数。
             * JREmptyDataSource ~ 数据源（和数据库数据源不同）。
             *      填充模板的数据来源：connection / javaBean / Map。
             *      填充空数据来源：JREmptyDataSource（如果为空或不写，可能有 bug）。
             */
            JasperPrint jasperPrint = JasperFillManager.fillReport(fileInputStream, new HashMap<>(), new JREmptyDataSource());

            // 将 JasperPrint 以 pdf 形式输出。
            JasperExportManager.exportReportToPdfStream(jasperPrint, responseOutputStream);

        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            responseOutputStream.flush();
        }

    }
}
