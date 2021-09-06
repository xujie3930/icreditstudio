package com.jinninghui.datasphere.icreditstudio.datasync.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum SourceTypeEnum {
    EXTERNAL_DATABASE(0, "外接数据库"),
    LOCAL_FILE(1, "本地文件"),
    BLOCK_CHAIN(2, "区块链");

    private Integer code;
    private String desc;

    public static SourceTypeEnum find(Integer code) {
        for (SourceTypeEnum value : SourceTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return EXTERNAL_DATABASE;
    }
}
