package com.carpcap.utils.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * poi 工具支持
 * @author CarpCap
 * @since 2025/12/3 18:06
 */
public class CExcelUtil {

    /**
     * @param ete         文件类型.
     * @param inputStream excel的文件流
     * @param consumer    consumer
     * @author CarpCap
     * @since 2025/12/3 18:08
     */
    public static void parseExcelIterator(CExcelTypeEnum ete, InputStream inputStream, Consumer<Iterator<Cell>> consumer) {
        parseExcelIterator(ete, inputStream, 0, 0, 0, consumer);
    }

    /**
     * @param ete         文件类型 .
     * @param inputStream excel的文件流.
     * @param sheetIndex  第几个sheet.
     * @param skipTopRows 跳过最上面行数量.
     * @param skipBottomRows 跳过最下面行数量.
     * @param consumer    consumer
     * @author CarpCap
     * @since 2025/12/3 18:06
     */
    public static void parseExcelIterator(CExcelTypeEnum ete, InputStream inputStream, int sheetIndex, int skipTopRows, int skipBottomRows, Consumer<Iterator<Cell>> consumer) {

        try {
            Workbook workbook = parseFile(inputStream, ete);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int rows = sheet.getLastRowNum() - skipBottomRows;
            for (int i = skipTopRows; i <= rows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                //判断前几列 如果没数据 就认为这一行是空的
                Iterator<Cell> iterator = row.iterator();
                String cellValue0 = getCellValue(row, 0, "");
                String cellValue1 = getCellValue(row, 1, "");
                String cellValue2 = getCellValue(row, 2, "");
                String cellValue3 = getCellValue(row, 3, "");
                String cellValue4 = getCellValue(row, 4, "");
                String cellValue5 = getCellValue(row, 5, "");
                String cellValue6 = getCellValue(row, 6, "");
                String str = cellValue0 + cellValue1 + cellValue2 + cellValue3 + cellValue4 + cellValue5 + cellValue6;
                if (str.isEmpty()) {
                    //如果这一行压根没数据 那就忽略掉
                    continue;
                }
                //执行
                consumer.accept(iterator);
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析Excel文件出现异常");
        }
    }


    private static Workbook parseFile(InputStream inputStream, CExcelTypeEnum ete) throws Exception {
        try (Workbook workbook = ete == CExcelTypeEnum.XLS ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream)) {
            return workbook;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static String getCellValue(Row row, int index, String defaultValue) {
        try {
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(row.getCell(index)).trim();
        } catch (Exception e) {
            return defaultValue;
        }
    }


}