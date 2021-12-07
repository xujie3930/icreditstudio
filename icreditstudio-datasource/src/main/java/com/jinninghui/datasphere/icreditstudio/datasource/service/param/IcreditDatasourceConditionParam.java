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
    /**
     * uri
     */
    private String uri;
    /**
     * 0：启用，1：停用
     */
    private Integer status;
    /**
     * 数据库名称
     */
    private String databaseName;
}
