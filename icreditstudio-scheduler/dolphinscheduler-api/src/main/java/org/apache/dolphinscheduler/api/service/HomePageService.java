package org.apache.dolphinscheduler.api.service;


import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;

public interface HomePageService {
    BusinessResult<Boolean> homePage(SchedulerHomepageRequest request);
}
