package com.jinninghui.datasphere.icreditstudio.datasync.service.increment;

import com.jinninghui.datasphere.icreditstudio.datasync.container.DialectKeyRegister;

/**
 * @author Peng
 */
public interface SyncQueryStatement extends DialectKeyRegister {

    /**
     * 同步查询语句
     * @param oldStatement
     * @param field
     * @param startTime
     * @param endTime
     * @return
     */
    String queryStatement(String oldStatement, String field, String startTime, String endTime);
}
