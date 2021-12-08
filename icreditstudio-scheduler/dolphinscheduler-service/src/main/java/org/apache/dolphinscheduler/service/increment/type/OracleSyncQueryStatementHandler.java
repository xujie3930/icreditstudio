package org.apache.dolphinscheduler.service.increment.type;

import org.apache.dolphinscheduler.service.increment.AbstractSyncQueryStatement;
import org.springframework.stereotype.Component;
import java.util.StringJoiner;

/**
 * @author lmh
 */
@Component
public class OracleSyncQueryStatementHandler extends AbstractSyncQueryStatement {
    @Override
    public String getDialect() {
        return "oracle";
    }

    @Override
    public String queryStatement(String oldStatement, String field, boolean isFirstFull, String startTime, String endTime) {
        StringJoiner condition = new StringJoiner(" ").add(field);
        if(isFirstFull){
            condition.add("<=")
                    .add("to_date(\'" + endTime + "\',\'YYYY-MM-DD HH24:MI:SS\')");
        }else {
            condition.add("between")
                    .add("to_date(\'" + startTime + "\'")
                    .add(",\'YYYY-MM-DD HH24:MI:SS\') and to_date(\'" + endTime + "\',\'YYYY-MM-DD HH24:MI:SS\')");
        }
        return super.splitJointSql(oldStatement, condition);
    }

}
