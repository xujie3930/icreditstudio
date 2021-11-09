package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.QueryField;

import java.util.List;

/**
 * @author Peng
 */
public interface DataSyncQuery extends DialectKeyRegister {
    /**
     * 数据同步语句
     *
     * @param queryFields
     * @param srcSql
     * @return
     */
    String querySql(List<QueryField> queryFields, String srcSql);
}
