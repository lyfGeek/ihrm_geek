package com.geek.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * 读取 Excel 并解析。
 * <p>
 * rowNum < sheet.getLastRowNum() + 1; ~ 最后一行的索引，要 +1。
 * cellNum < row.getLastCellNum(); ~ 最后一个单元格的号码。
 */
public class PoiRead {

    public static void main(String[] args) throws IOException {

        // 根据 excel 文件创建工作薄。
        Workbook workbook = new XSSFWorkbook("C:\\Users\\geek\\Desktop\\demo.xlsx");
        // demo.xlsx
        // 获取 sheet。
        Sheet sheet = workbook.getSheetAt(0);
        // 获取 sheet 的每一行，和每一个单元格。
        for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum++) {
            Row row = sheet.getRow(rowNum);// 根据索引获取每一行。
            StringBuilder stringBuilder = new StringBuilder();
            for (int cellNum = 2; cellNum < row.getLastCellNum(); ++cellNum) {
                Cell cell = row.getCell(cellNum);// 根据索引获取每一单元格。
                // 获取每一个单元格的内容。
                Object cellValue = getCellValue(cell);
                stringBuilder.append(cellValue).append(" ~ ");
            }
            System.out.println(stringBuilder.toString());
        }
    }

    private static Object getCellValue(Cell cell) {
        // 获取单元格的属性类型。
        CellType cellType = cell.getCellType();
        // 根据单元格数据类型获取数据。
        Object cellValue = null;

        switch (cellType) {
            case NUMERIC:// = 0; 数字（日期、普通数字等）。
//                System.out.print("【~ ~ ~ CELL_TYPE ~ numeric ～ 0 ~ ~ ~】");
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 日期。
//                    System.out.print("【~ ~ ~ CELL_TYPE ~ numeric ～ 0 ～ 日期。~ ~ ~】");
                    cellValue = cell.getDateCellValue();
                } else {
//                    System.out.print("【~ ~ ~ CELL_TYPE ~ numeric ～ 0 ～ 转换为字符串输出。~ ~ ~】");
//                     如果不是日期格式，防止数字过长。
//                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cellValue = cell.getNumericCellValue();
                }
                break;

            case STRING:// = 1;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ String ～ 1 ~ ~ ~】");
                cellValue = cell.getStringCellValue();
                break;

            case FORMULA:// = 2;
//                System.out.println("~ ~ ~ CELL_TYPE ~ formula ～ 2 ~ ~ ~");
                cellValue = cell.getCellFormula();
                break;

//            case HSSFCell.CELL_TYPE_BLANK:// = 3;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ blank ～ 3 ~ ~ ~】");
//                break;

            case BOOLEAN:// = 4;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ boolean ～ 4 ~ ~ ~】");
                cellValue = cell.getBooleanCellValue();
                break;

//            case HSSFCell.CELL_TYPE_ERROR:// = 5;
//                System.out.print("【~ ~ ~ CELL_TYPE ~ error ～ 5 数据类型错误。~ ~ ~】");
//                cellValue = String.valueOf(cell.getErrorCellValue());
//                break;

            default:
                break;
        }

        return cellValue;
    }
}
