package com.carpcap.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Consumer;

/**
 * EasyExcel支持 流式 内存占用低
 * @author CarpCap
 * @since 2025/12/19 16:43
 */
public class CEasyExcelUtil {

    /**
     * @param inputStream excel的文件流
     * @param consumer    consumer
     * @author CarpCap
     * @since 2025/12/3 18:08
     */
    public static void parseExcel(InputStream inputStream, Consumer<Map<Integer, String>> consumer) {
        parseExcel(inputStream, 0, 0, consumer);
    }

    /**
     * @param inputStream excel的文件流.
     * @param sheetIndex  第几个sheet.
     * @param skipTopRows 跳过最上面行数量.
     * @param consumer    consumer
     * @author CarpCap
     * @since 2025/12/3 18:06
     */
    /**
     * 流式解析 Excel，大幅降低内存使用
     */
    public static void parseExcel(InputStream inputStream, int sheetIndex, int skipTopRows, Consumer<Map<Integer, String>> consumer) {
        try {
            EasyExcel.read(inputStream, new AnalysisEventListener<Map<Integer, String>>() {
                @Override
                public void invoke(Map<Integer, String> data, AnalysisContext context) {
                    // 1. 跳过前 N 行
                    if (context.readRowHolder().getRowIndex() < skipTopRows) {
                        return;
                    }

                    // 2. 检查前几列是否有数据（模拟你原有的 str.isEmpty 判断）
                    String value = data.get(0) + data.get(1) + data.get(2) + data.get(3) + data.get(5) + data.get(6);
                    boolean hasData = value != null && !value.trim().isEmpty();


                    // 3. 执行业务逻辑
                    if (hasData) {
                        consumer.accept(data);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                }
            }).sheet(sheetIndex).doRead();
        } catch (Exception e) {
            throw new RuntimeException("流式解析Excel异常", e);
        }
    }

}