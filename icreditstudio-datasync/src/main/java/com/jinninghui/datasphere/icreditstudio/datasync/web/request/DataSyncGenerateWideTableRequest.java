package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.FileAssociated;
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
    @NotBlank(message = "工作空间ID不能为空")
    private String workspaceId;
    /**
     * 目标库名称
     */
    @NotBlank(message = "目标库名称不能为空")
    private String targetSource;
    /**
     * 宽表名称
     */
    @NotBlank(message = "宽表名称不能为空")
    private String wideTableName;
    /**
     * 分区字段
     */
    @NotBlank(message = "分区字段不能为空")
    private String partition;
    /**
     * 资源类型【0：外部数据库，1：本地文件，2：区块链数据】
     */
    @NotNull(message = "资源类型不能为空")
    private Integer sourceType;

    private List<String> sourceTables;
    /**
     * 关联关系
     */
    private List<FileAssociated> view;
}
