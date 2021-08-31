package com.jinninghui.datasphere.icreditstudio.datasource.service.param;

import lombok.Builder;
import lombok.Data;

/**
 * @author Peng
 */
@Data
@Builder
public class IcreditDatasourceConditionParam {
    private String workspaceId;

    private Integer category;

}
