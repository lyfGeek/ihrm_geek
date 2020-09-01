package com.geek.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class PoiTest01 {

    public static void main(String[] args) throws IOException {
        // 创建工作簿。
        Workbook workbook = new XSSFWorkbook();
        // HSSF 提供读写 Microsoft Excel XLS 式档案的功能。
        // XSSF 提供读写 Microsoft Excel OOXML XLSX 格式档案的功能。
        // HWPF 提供读写 Microsoft Word DOC 格式档案的功能。
        // HSLF 提供读写 Microsoft PowerPoint 格式档案的功能。
        // HDGF 提供读写 Microsoft Visio 格式档案的功能。
        // HPBF 提供读写 Microsoft Publisher 格式档案的功能。
        // HSMF 提供读写 Microsoft Outlook 格式档案的功能。

        // 创建表单。
        Sheet sheet = workbook.createSheet("test");

        // 创建行对象。索引从 0 开始。
        Row row = sheet.createRow(2);
        // 创建单元格对象。索引从 0 开始。
        Cell cell = row.createCell(2);
        // 向单元格中写入内容。
        cell.setCellValue("hello， Geek。");

        // 文件流。
        FileOutputStream fileOutputStream = new FileOutputStream("./test.xlsx");
        // 写入文件。
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}