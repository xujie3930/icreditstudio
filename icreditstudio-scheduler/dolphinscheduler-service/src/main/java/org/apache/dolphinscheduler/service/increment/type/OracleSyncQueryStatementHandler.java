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
public class OracleSyncQueryStatementHandler extends AbstractSyncQueryStatement {

    private final static String FORMAT_STR = "yyyy-mm-dd hh24:mi:ss";

    @Override
    public String getDialect() {
        return "oracle";
    }

    @Override
    public String queryStatement(String oldStatement, String field, String startTime, String endTime) {
        StringBuilder condition = new StringBuilder(field)
                .append(" between")
                .append(" to_date(").append("\'" + startTime + "\'").append(",").append("\'" + FORMAT_STR + "\'").append(")")
                .append(" and")
                .append(" to_date(").append("\'" + endTime + "\'").append(",").append("\'" + FORMAT_STR + "\'").append(")");
        if (StringUtils.contains(oldStatement, "where")) {
            List<String> where = StrUtil.split(oldStatement, "where");
            return new StringJoiner(" ").add(where.get(0).trim()).add("where").add(condition).toString();
        } else {
            return new StringJoiner(" ").add(oldStatement).add("where").add(condition).toString();
        }
    }
}
