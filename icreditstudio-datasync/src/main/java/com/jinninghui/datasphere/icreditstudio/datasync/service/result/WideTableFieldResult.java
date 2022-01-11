package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class WideTableFieldResult extends WideTableFieldInfo {
    private List<Object> fieldType;

    private String associateDictLabel;
}
