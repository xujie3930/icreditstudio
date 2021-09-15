package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncGenerateWideTableParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTable;

import java.util.List;

/**
 * @author Peng
 */
public interface GenerateWideTable extends DialectKeyRegister {

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
    boolean verifySql(DataSyncGenerateWideTableParam param);

    /**
     * 数据源ID
     *
     * @param param
     * @return
     */
    String getDataSourceId(DataSyncGenerateWideTableParam param);

    /**
     * 获取表信息
     *
     * @param param
     * @return
     */
    List<TableInfo> getTableInfos(DataSyncGenerateWideTableParam param);

    /**
     * 生成宽表
     *
     * @param sql
     * @param datasourceId
     * @param tableInfos
     * @return
     */
    WideTable generate(String sql, String datasourceId, List<TableInfo> tableInfos);
}
