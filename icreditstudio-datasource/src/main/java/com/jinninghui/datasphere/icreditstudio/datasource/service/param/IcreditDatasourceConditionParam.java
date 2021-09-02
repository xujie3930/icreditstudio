package com.jinninghui.datasphere.icreditstudio.datasource.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author Peng
 */
@Data
@Builder
public class IcreditDatasourceConditionParam {
    /**
     * 工作空间ID
     */
    private String workspaceId;
    /**
     * 分类
     */
    private Set<Integer> category;
    /**
     * 数据源ID
     */
    private String datasourceId;
}
