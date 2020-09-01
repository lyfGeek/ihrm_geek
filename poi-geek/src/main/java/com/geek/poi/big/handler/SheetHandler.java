package com.geek.poi.big.handler;

import com.geek.poi.big.entity.PoiEntity;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * 自定义的事件处理器。
 * 处理每一行数据读取。
 */
public class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private PoiEntity poiEntity;

    /**
     * A header or footer has been encountered
     *
     * @param text
     * @param isHeader
     * @param tagName
     */
    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {

    }

    /**
     * Signal that the end of a sheet was been reached
     */
    @Override
    public void endSheet() {

    }

    /**
     * A row with the (zero based) row number has started
     * <p>
     * 开始解析某一行的时候触发。
     *
     * @param rowNum 行索引。
     */
    @Override
    public void startRow(int rowNum) {
        // 实例化对象。
        if (rowNum > 1) {
            poiEntity = new PoiEntity();
        }
    }

    /**
     * A row with the (zero based) row number has ended
     * <p>
     * 结束解析某一行的时候触发。
     *
     * @param rowNum 行索引。
     */
    @Override
    public void endRow(int rowNum) {
        // 使用对象进行业务操作。
        System.out.println("poiEntity = " + poiEntity);
    }

    /**
     * A cell, with the given formatted value (may be null),
     * and possibly a comment (may be null), was encountered.
     * <p>
     * Sheets that have missing or empty cells may result in
     * sparse calls to <code>cell</code>. See the code in
     * <code>src/examples/src/org/apache/poi/xssf/eventusermodel/XLSX2CSV.java</code>
     * for an example of how to handle this scenario.
     * <p>
     * 对行中的每一个单元格进行处理。
     *
     * @param cellReference  单元格名称。
     * @param formattedValue 数据。
     * @param comment        批注。
     */
    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        // 对对象属性进行赋值。
        if (poiEntity != null) {
            String prefix = cellReference.substring(0, 1);
            switch (prefix) {
                case "A":
                    poiEntity.setId(formattedValue);
                    break;
                case "B":
                    poiEntity.setBreast(formattedValue);
                    break;
                case "C":
                    poiEntity.setAdipocytes(formattedValue);
                    break;
                case "D":
                    poiEntity.setNegative(formattedValue);
                    break;
                case "E":
                    poiEntity.setStaining(formattedValue);
                    break;
                case "F":
                    poiEntity.setSupportive(formattedValue);
                    break;
                default:
                    break;
            }
        }
    }
}
