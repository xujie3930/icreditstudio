package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum DefaultDelimiterEnum {
    TABS("\t", "制表符"),
    COMMA(",", "逗号"),
    ;
    private String symbol;
    private String name;

    public static DefaultDelimiterEnum find(String symbol) {
        for (DefaultDelimiterEnum value : DefaultDelimiterEnum.values()) {
            if (value.symbol.equals(symbol)) {
                return value;
            }
        }
        return TABS;
    }
}
