package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncGenerateWideTableParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTable;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DataSyncGenerateWideTableRequest;

import java.util.List;

/**
 * @author Peng
 */
public interface GenerateWideTable extends DialectKeyRegister {

    /**
     * 根据参数判断当前类型
     *
     * @param param
     * @return
     */
    boolean isCurrentWideTable(DataSyncGenerateWideTableParam param);

    /**
     * 获取宽表sql
     *
     * @param param
     * @return
     */
    String getWideTableSql(DataSyncGenerateWideTableParam param);

    /**
     * 校验sql
     *
     * @param param
     * @return
     */
    boolean verifySql(String sql, DataSyncGenerateWideTableParam param);

    /**
     * 检查sq
     *
     * @param sql
     * @return
     */
    List<DataSyncGenerateWideTableRequest.DatabaseInfo> checkDatabaseFromSql(String sql);

    /**
     * 数据源ID
     *
     * @param sql
     * @param param
     * @return
     */
    String getDataSourceId(String sql, DataSyncGenerateWideTableParam param);

    /**
     * 生成宽表
     *
     * @param sql
     * @param datasourceId
     * @return
     */
    WideTable generate(String sql, String datasourceId);
}
