package com.geek.poi.big;

import com.geek.poi.big.handler.SheetHandler;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 使用事件模型解析百万数据 Excel 报表。
 */
public class PoiTest {

    public static void main(String[] args) throws OpenXML4JException, IOException, SAXException {
        String path = "./demo.xlsx";

        // 根据 Excel 报表获取 OPCPackage。
        // （以 xml 形式打开）。
        OPCPackage opcPackage = OPCPackage.open(path, PackageAccess.READ);

        // 创建 XSSFReader。
        XSSFReader xssfReader = new XSSFReader(opcPackage);

        // 获取 SharedStringTable 对象。
        SharedStringsTable sharedStringsTable = xssfReader.getSharedStringsTable();

        // 获取 styleTable 对象。
        StylesTable stylesTable = xssfReader.getStylesTable();

        // 创建 Sax 的 xmlReader 对象。
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();

        // 注册事件处理器。
        XSSFSheetXMLHandler xssfSheetXMLHandler = new XSSFSheetXMLHandler(stylesTable, sharedStringsTable, new SheetHandler(), false);

        xmlReader.setContentHandler(xssfSheetXMLHandler);

        // 逐行读取。
        XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        while (sheetIterator.hasNext()) {
            InputStream inputStream = sheetIterator.next();// 每一个 sheet 的流数据。
            InputSource inputSource = new InputSource(inputStream);// 每个 sheet 的所有内容。
            xmlReader.parse(inputSource);
        }
    }
}
