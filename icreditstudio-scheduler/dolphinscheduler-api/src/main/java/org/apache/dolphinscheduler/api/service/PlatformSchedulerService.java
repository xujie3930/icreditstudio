package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.api.param.CreateSchedulerParam;
import org.apache.dolphinscheduler.api.service.result.CreateSchedulerResult;

/**
 * @author Peng
 */
public interface PlatformSchedulerService {

    /**
     * 给流程添加定时
     *
     * @param param
     * @return
     */
    CreateSchedulerResult createSchedule(CreateSchedulerParam param);

    /**
     * delete schedule
     *
     * @param projectCode project code
     * @param scheduleId  schedule id
     * @throws RuntimeException runtime exception
     */
    void deleteSchedule(String projectCode, String scheduleId);
}
