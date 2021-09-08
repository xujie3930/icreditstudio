package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Peng
 */
@Data
public class DataSyncTaskBuildSaveParam {
    @NotBlank(message = "60000016")
    private String taskId;
    /**
     * 目标库名
     */
    @NotBlank(message = "60000001")
    private String targetSource;
    /**
     * 宽表名称
     */
    @NotBlank(message = "60000002")
    private String wideTableName;
    /**
     * 分区字段
     */
    private String partition;

    /**
     * 宽表字段查询SQL
     */
    private String wideTableSql;
    /**
     * 【0：外部数据库，1：本地文件，2：区块链数据】
     */
    @Range(max = 2, message = "60000017")
    @NotNull(message = "60000013")
    private Integer sourceType;

    /**
     * 数据源方言
     */
    @NotBlank(message = "60000004")
    private String dialect;

    /**
     * 连接表集合
     */
    @NotNull(message = "60000018")
    private List<TableInfo> sourceTables;
    /**
     * 关联关系
     */
    private List<AssociatedData> view;
    /**
     * 宽表字段
     */
    @NotNull(message = "60000014")
    private List<WideTableFieldInfo> fieldInfos;

    private Integer version;

    private String targetUrl;
}
