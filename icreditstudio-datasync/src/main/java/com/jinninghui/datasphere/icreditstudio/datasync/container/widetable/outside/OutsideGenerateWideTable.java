package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.outside;

import com.jinninghui.datasphere.icreditstudio.datasync.container.DialectKeyRegister;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.OutsideSourceWideTableParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTable;

/**
 * @author Peng
 */
public interface OutsideGenerateWideTable extends DialectKeyRegister {

    /**
     * 根据参数判断当前类型
     *
     * @param param
     * @return
     */
    boolean isCurrentTypeHandler(OutsideSourceWideTableParam param);

    /**
     * 获取宽表sql
     *
     * @param param
     * @return
     */
    String getWideTableSql(OutsideSourceWideTableParam param);

    /**
     * 数据源ID
     *
     * @param sql
     * @param param
     * @return
     */
    String getDataSourceId(String sql, OutsideSourceWideTableParam param);

    /**
     * 生成宽表
     *
     * @param sql
     * @param datasourceId
     * @return
     */
    WideTable generate(String sql, String datasourceId);
}
