package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable;

import com.jinninghui.datasphere.icreditstudio.datasync.container.DialectKeyRegister;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.PreSqlPositionDataSourceResult;

import java.util.List;

/**
 * @author Peng
 */
public interface QueryStatementParseHandler extends DialectKeyRegister {

    /**
     * 根据参数判断当前类型
     *
     * @param queryStat
     * @return
     */
    boolean isCurrentHandler(String queryStat);

    /**
     * 根据sql解析得到的数据源列表信息
     *
     * @param sourceType
     * @param queryStat
     * @return
     */
    List<PreSqlPositionDataSourceResult.DatabaseInfo> getDataSourceInfo(Integer sourceType, String queryStat);
}
