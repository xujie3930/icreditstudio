package org.apache.dolphinscheduler.service.increment.type;

import org.apache.dolphinscheduler.service.increment.AbstractSyncQueryStatement;
import org.springframework.stereotype.Component;
import java.util.StringJoiner;

/**
 * @author lmh
 */
@Component
public class OracleSyncQueryStatementHandler extends AbstractSyncQueryStatement {

    private final static String FORMAT_STR = "YYYY-MM-DD HH24:MI:SS";

    @Override
    public String getDialect() {
        return "oracle";
    }

    @Override
    public StringJoiner getSqlWhere(String field, boolean isFirstFull, String startTime, String endTime){
        StringJoiner condition = new StringJoiner(" ").add(field);
        if(isFirstFull){
            condition.add("<=")
                    .add("to_date(\'" + endTime + "\',\'" + FORMAT_STR + "\')");
        }else {
            condition.add("between")
                    .add("to_date(\'" + startTime + "\'")
                    .add(",\'" + FORMAT_STR + "\') and to_date(\'" + endTime + "\',\'" + FORMAT_STR + "\')");
        }
        return condition;
    }

}
