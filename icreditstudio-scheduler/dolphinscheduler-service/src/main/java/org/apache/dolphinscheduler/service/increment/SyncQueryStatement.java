package org.apache.dolphinscheduler.service.increment;

import org.apache.dolphinscheduler.service.DialectKeyRegister;

/**
 * @author Peng
 */
public interface SyncQueryStatement extends DialectKeyRegister {

    /**
     * 同步查询语句
     *
     * @param oldStatement
     * @param field
     * @param isFirstFull
     * @param startTime
     * @param endTime
     * @return
     */
    String queryStatement(String oldStatement, String field, boolean isFirstFull, String startTime, String endTime);
}
