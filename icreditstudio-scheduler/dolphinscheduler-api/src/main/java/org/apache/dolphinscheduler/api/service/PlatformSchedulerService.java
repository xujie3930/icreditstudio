package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
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
    BusinessResult<CreateSchedulerResult> createSchedule(CreateSchedulerParam param);

    /**
     * delete schedule
     *
     * @param projectCode project code
     * @param scheduleId  schedule id
     * @throws RuntimeException runtime exception
     */
    void deleteSchedule(String projectCode, String scheduleId);
}
