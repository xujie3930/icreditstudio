package org.apache.dolphinscheduler.api.service;


import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.result.*;

import java.util.List;

public interface HomePageService {
    BusinessResult<TaskRoughResult> rough(String userId, SchedulerHomepageRequest request);

    BusinessResult<List<TaskSituationResult>> situation(String userId, String workspaceId);

    BusinessResult<List<TaskCountResult>> taskCount(String userId, SchedulerHomepageRequest request);

    BusinessResult<List<RuntimeRankResult>> runtimeRank(String userId, SchedulerHomepageRequest request);

    BusinessResult<List<RunErrorRankResult>> runErrorRank(String userId, SchedulerHomepageRequest request);

    WorkBenchResult workbench(String userId, String id);
}
