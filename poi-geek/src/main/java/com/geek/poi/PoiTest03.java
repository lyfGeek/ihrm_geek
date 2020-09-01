package com.geek.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片。
 */
public class PoiTest03 {

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

        // 读取图片流。
        FileInputStream fileInputStream = new FileInputStream("G:\\geek\\png。\\0.jpg");

        // 转换二进制数组。
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        fileInputStream.read(bytes);

        // 向 POI 内存中添加一张图片，返回图片在集合中的索引。
        int index = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);

        // 绘制图片工具类。
        CreationHelper creationHelper = workbook.getCreationHelper();

        // 创建一个绘图对象。
        Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();

        // 创建描点，设置图片坐标。
        ClientAnchor clientAnchor = creationHelper.createClientAnchor();
        clientAnchor.setRow1(0);// 从第 0 行开始。
        clientAnchor.setCol1(0);// 从第 0 列开始。

        // 绘制图片。
        Picture picture = drawingPatriarch.createPicture(clientAnchor, index);// 图片位置，图片索引。
        picture.resize();// 自适应渲染图片。

        // 文件流。
        FileOutputStream fileOutputStream = new FileOutputStream("./test.xlsx");
        // 写入文件。
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
