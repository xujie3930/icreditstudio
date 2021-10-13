package org.apache.dolphinscheduler.api.service;


import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.result.RunErrorRank;
import org.apache.dolphinscheduler.api.service.result.TaskCount;
import org.apache.dolphinscheduler.api.service.result.TaskRough;
import org.apache.dolphinscheduler.api.service.result.TaskSituation;

import java.util.List;

public interface HomePageService {
    BusinessResult<TaskRough> rough(SchedulerHomepageRequest request);

    BusinessResult<List<TaskSituation>> situation(String workspaceId);

    BusinessResult<List<TaskCount>> taskCount(SchedulerHomepageRequest request);

    BusinessResult<BusinessPageResult> runtimeRank(SchedulerHomepageRequest request);

    BusinessResult<List<RunErrorRank>> runErrorRank(SchedulerHomepageRequest request);
}
