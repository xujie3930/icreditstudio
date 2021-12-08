package test;

import org.apache.dolphinscheduler.service.increment.IncrementUtil;
import org.apache.dolphinscheduler.service.increment.type.MysqlSyncQueryStatementHandler;
import org.apache.dolphinscheduler.service.increment.type.OracleSyncQueryStatementHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Peng
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {IncrementUtil.class, MysqlSyncQueryStatementHandler.class, OracleSyncQueryStatementHandler.class})
public class JsonHandlerTest {

    @Test
    public void IncrementUtilTest() {
        String oldStatement = "select * from ICREDITSTUDIO.TABLE_TEST";
        String dialect = "oracle";
        String field = "create_time";
        String startTime = "2021-12-08 00:00:00";
        String endTime = "2021-12-08 23:59:59";
        String timeIncQueryStatement = IncrementUtil.getTimeIncQueryStatement(oldStatement, dialect, field, startTime, endTime);
        System.out.println(timeIncQueryStatement);
    }
}
