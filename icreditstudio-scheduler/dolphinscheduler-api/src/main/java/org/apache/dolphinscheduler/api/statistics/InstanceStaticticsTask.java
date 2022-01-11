package org.apache.dolphinscheduler.api.statistics;

import com.hashtech.businessframework.utils.CollectionUtils;
import org.apache.dolphinscheduler.api.enums.StatisticsType;
import org.apache.dolphinscheduler.api.enums.TaskExecStatusEnum;
import org.apache.dolphinscheduler.api.service.StatisticsDefinitionService;
import org.apache.dolphinscheduler.api.service.StatisticsInstanceService;
import org.apache.dolphinscheduler.api.service.TaskInstanceService;
import org.apache.dolphinscheduler.common.enums.ExecutionStatus;
import org.apache.dolphinscheduler.dao.entity.StatisticsDefinitionEntity;
import org.apache.dolphinscheduler.dao.entity.StatisticsInstanceEntity;
import org.apache.dolphinscheduler.dao.entity.result.TaskInstanceStatisticsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author xujie
 * @description 统计taskInstance表数据
 * @create 2021-12-17 11:34
 **/
@Component
@Lazy(false)
//TODO：这里暂且用扫描全表方式，后续再变更为分布式的调度框架
public class InstanceStaticticsTask {

    @Autowired
    private TaskInstanceService taskInstanceService;
    @Autowired
    private StatisticsInstanceService instanceService;
    @Autowired
    private StatisticsDefinitionService definitionService;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void instanceStatictics() throws InterruptedException {
        //扫描未被扫描的记录
        List<TaskInstanceStatisticsResult> taskInstanceList = taskInstanceService.selectInstanceListByScanState(StatisticsType.NONE.ordinal());
        if (CollectionUtils.isEmpty(taskInstanceList)) {
            return;
        }
        for (TaskInstanceStatisticsResult taskInstance : taskInstanceList) {
            try {
                taskInstanceService.statictics(taskInstance);
            } catch (Exception e) {
                continue;
            }
        }
    }
}
