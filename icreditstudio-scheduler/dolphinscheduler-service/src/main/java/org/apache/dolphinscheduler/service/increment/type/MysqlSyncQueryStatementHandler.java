package org.apache.dolphinscheduler.service.increment.type;

import org.apache.dolphinscheduler.service.increment.AbstractSyncQueryStatement;
import org.springframework.stereotype.Component;
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
    public String queryStatement(String oldStatement, String field, boolean isFirstFull, String startTime, String endTime) {
        // select * from aaa
        StringJoiner condition = new StringJoiner(" ").add(field);
        if(isFirstFull){
            condition.add("<=")
                    .add("\'" + endTime + "\'");
        }else {
            condition.add("between")
                    .add("\'" + startTime + "\'")
                    .add("and")
                    .add("\'" + endTime + "\'");
        }
        return super.splitJointSql(oldStatement, condition);
    }
}
