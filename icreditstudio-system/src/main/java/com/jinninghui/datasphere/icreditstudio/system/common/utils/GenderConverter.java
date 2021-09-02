package com.jinninghui.datasphere.icreditstudio.system.common.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * EasyExcel自定义性别转换器
 *
 * @author EDZ
 */
public class GenderConverter implements Converter<String> {

    public static final String MALE = "男";
    public static final String FEMALE = "女";

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
        if (MALE.equals(stringValue)) {
            return "1";
        }
        if (FEMALE.equals(stringValue)) {
            return "0";
        }
        return null;
    }

    @Override
    public CellData convertToExcelData(String msg, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }
}

