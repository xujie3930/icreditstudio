package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Peng
 */
@Data
public class DataSyncGenerateWideTableRequest {

    /**
     * 工作空间ID
     */
    @NotBlank(message = "60000000")
    private String workspaceId;
    /**
     * 目标库名称
     */
    @NotBlank(message = "60000001")
    private String targetSource;
    /**
     * 宽表名称
     */
    @NotBlank(message = "60000002")
    private String wideTableName;
    /**
     * 资源类型【0：外部数据库，1：本地文件，2：区块链数据】
     */
    @NotNull(message = "60000003")
    private String datasourceId;
    /**
     * 数据源方言
     */
    @NotBlank(message = "60000004")
    private String dialect;
    /**
     * 连接表集合
     */
    @NotNull(message = "60000005")
    private List<String> sourceTables;
    /**
     * 关联关系
     */
    private List<AssociatedData> view;
}
