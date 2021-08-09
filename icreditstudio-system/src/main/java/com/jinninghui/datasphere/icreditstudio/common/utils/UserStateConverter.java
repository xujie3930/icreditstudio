package com.jinninghui.datasphere.icreditstudio.common.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * EasyExcel自定义状态转换器
 * @author EDZ
 */
public class UserStateConverter implements Converter<String> {

    public static final String ENABLE = "启用";
    public static final String DISABLE = "不启用";

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        if (ENABLE.equals(stringValue)){
            return "N";
        }
        if(DISABLE.equals(stringValue)){
            return "Y";
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(String msg, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }
}

