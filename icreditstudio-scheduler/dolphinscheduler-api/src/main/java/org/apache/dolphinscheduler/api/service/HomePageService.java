package org.apache.dolphinscheduler.api.service;


import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.result.RunErrorRankResult;
import org.apache.dolphinscheduler.api.service.result.TaskCountResult;
import org.apache.dolphinscheduler.api.service.result.TaskRoughResult;
import org.apache.dolphinscheduler.api.service.result.TaskSituationResult;

import java.util.List;

public interface HomePageService {
    BusinessResult<TaskRoughResult> rough(SchedulerHomepageRequest request);

    BusinessResult<List<TaskSituationResult>> situation(String workspaceId);

    BusinessResult<List<TaskCountResult>> taskCount(SchedulerHomepageRequest request);

    BusinessResult<BusinessPageResult> runtimeRank(SchedulerHomepageRequest request);

    BusinessResult<List<RunErrorRankResult>> runErrorRank(SchedulerHomepageRequest request);
}
