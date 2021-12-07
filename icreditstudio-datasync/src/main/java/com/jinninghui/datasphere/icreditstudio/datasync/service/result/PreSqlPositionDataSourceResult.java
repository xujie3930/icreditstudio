package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class PreSqlPositionDataSourceResult {
    /**
     * 是否弹框显示
     */
    private boolean showWindow;
    /**
     * 不同主机相同数据库信息列表
     */
    private List<DatabaseInfo> sameNameDataBase;

    @Data
    public static class DatabaseInfo {
        private String databaseName;
        private String host;
        private String dialect;
        private String datasourceId;
    }
}
