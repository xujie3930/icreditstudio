package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param;

import lombok.Builder;
import lombok.Data;

/**
 * @author Peng
 */
@Data
@Builder
public class CodeInfoQueryConditionParam {
    private String codeType;
    private boolean groupBy;
    private String groupByField;
}
