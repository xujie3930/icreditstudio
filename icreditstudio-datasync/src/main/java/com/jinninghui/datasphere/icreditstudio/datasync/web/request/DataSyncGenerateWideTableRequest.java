package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class DataSyncGenerateWideTableRequest {
    /**
     * 【0：外部数据库，1：本地文件，2：区块链数据】
     */
    private Integer sourceType;
    /**
     * 创建方式【0：可视化，1，SQL】
     */
    private Integer createMode;
    /**
     * 查询语句
     */
    private String sql;

    /**
     * 数据源ID
     */
    private String datasourceId;
    /**
     * 数据源方言
     */
    private String dialect;
    /**
     * 连接表集合
     */
    private List<TableInfo> sourceTables;
    /**
     * 关联关系
     */
    private List<AssociatedData> view;

    @Data
    public static class DatabaseInfo {
        private String databaseName;
        private String host;
        private String datasourceId;
    }
}
