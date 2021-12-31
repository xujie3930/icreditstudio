package com.jinninghui.datasphere.icreditstudio.datasource.web.request;

import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.SourceTypeTransferEnum;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

/**
 * @author Peng
 */
@Data
public class DataSourcesQueryRequest {

    private String workspaceId;

    private Integer sourceType;
    /**
     * 数据源名称
     */
    private String databaseName;
    /**
     * 数据源ID
     */
    private String datasourceId;

    public Set<Integer> getCategory() {
        Set<Integer> result = Sets.newHashSet();
        if (Objects.nonNull(sourceType)) {
            result = SourceTypeTransferEnum.getCatalogue(sourceType);
        }
        return result;
    }
}
