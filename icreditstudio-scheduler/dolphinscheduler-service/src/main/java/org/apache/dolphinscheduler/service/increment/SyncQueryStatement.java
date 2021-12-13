package org.apache.dolphinscheduler.service.increment;

import org.apache.dolphinscheduler.service.DialectKeyRegister;

import java.util.StringJoiner;

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

    /**
     * 生成querySql where 后面的条件部分
     * @param field
     * @param isFirstFull
     * @param startTime
     * @param endTime
     * @return
     */
    StringJoiner getSqlWhere(String field, boolean isFirstFull, String startTime, String endTime);
}
