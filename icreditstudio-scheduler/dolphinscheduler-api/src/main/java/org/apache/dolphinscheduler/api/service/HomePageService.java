package org.apache.dolphinscheduler.api.service;


import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.result.*;

import java.util.List;

public interface HomePageService {
    BusinessResult<TaskRoughResult> rough(SchedulerHomepageRequest request);

    BusinessResult<List<TaskSituationResult>> situation(String workspaceId);

    BusinessResult<List<TaskCountResult>> taskCount(SchedulerHomepageRequest request);

    BusinessResult<List<RuntimeRankResult>> runtimeRank(SchedulerHomepageRequest request);

    BusinessResult<List<RunErrorRankResult>> runErrorRank(SchedulerHomepageRequest request);
}
