package com.carpcap.utils.excel;

/**
 * @author CarpCap
 * @since 2025/12/3 17:43
 */
public enum CExcelTypeEnum {

    XLSX("xlsx"),
    XLS("xls"),
    ;


    final String value;

    CExcelTypeEnum(String value) {
        this.value = value;
    }
}
