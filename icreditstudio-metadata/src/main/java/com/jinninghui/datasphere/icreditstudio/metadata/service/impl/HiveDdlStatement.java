package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import com.jinninghui.datasphere.icreditstudio.metadata.service.param.WideTableField;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class HiveDdlStatement {
    private String ddlType;
    private List<WideTableField> fieldList;
    private String delimiter;
    private String partition;
}
