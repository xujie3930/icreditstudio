package org.apache.dolphinscheduler.service.increment;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author Peng
 */
public abstract class AbstractSyncQueryStatement implements SyncQueryStatement {

    public AbstractSyncQueryStatement() {
        register();
    }

    @Override
    public void register() {
        SyncQueryStatementContainer.getInstance().put(getDialect(), this);
    }

    protected String splitJointSql(String oldStatement, StringJoiner condition){
        if (StringUtils.contains(oldStatement, "where")) {
            List<String> where = StrUtil.split(oldStatement, "where");
            return new StringJoiner(" ").add(where.get(0).trim()).add("where").merge(condition).toString();
        } else {
            return new StringJoiner(" ").add(oldStatement).add("where").merge(condition).toString();
        }
    }
}
