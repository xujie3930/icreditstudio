package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.result.ScheduleLogPageResult;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.param.LogPageParam;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.apache.dolphinscheduler.common.vo.DispatchLogVO;

public interface DispatchService {

    BusinessResult<BusinessPageResult<DispatchTaskPageResult>> dispatchPage(DispatchTaskPageParam param);

    BusinessResult<Boolean> reStartOrStop(String processInstanceId, String execType);

    int executeInstance(String instanceId, String execType);

    BusinessResult<ScheduleLogPageResult<DispatchLogVO>> logPage(LogPageParam param);

    BusinessResult<Boolean> nowRun(String taskId, String execType);
}
