package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * @author Peng
 */
@Data
@Builder
public class CodeInfoQueryConditionParam {
    private String codeName;
    private boolean groupBy;
    private String groupByField;

    private Collection<String> types;
}
