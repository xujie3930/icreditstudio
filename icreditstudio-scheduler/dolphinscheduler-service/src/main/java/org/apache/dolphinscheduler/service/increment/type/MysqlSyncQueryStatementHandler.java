package org.apache.dolphinscheduler.service.increment.type;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.service.increment.AbstractSyncQueryStatement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author Peng
 */
@Component
public class MysqlSyncQueryStatementHandler extends AbstractSyncQueryStatement {
    @Override
    public String getDialect() {
        return "mysql";
    }

    @Override
    public String queryStatement(String oldStatement, String field, String startTime, String endTime) {
        // select * from aaa

        StringJoiner condition = new StringJoiner(" ").add(field)
                .add("between")
                .add("\'" + startTime + "\'")
                .add("and")
                .add("\'" + endTime + "\'");
        if (StringUtils.contains(oldStatement, "where")) {
            List<String> where = StrUtil.split(oldStatement, "where");
            return new StringJoiner(" ").add(where.get(0).trim()).add("where").merge(condition).toString();
        } else {
            return new StringJoiner(" ").add(oldStatement).add("where").merge(condition).toString();
        }
    }
}
