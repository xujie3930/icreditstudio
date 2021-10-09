package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.utils.DateUtils;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.HomePageService;
import org.apache.dolphinscheduler.api.service.ProcessInstanceService;
import org.apache.dolphinscheduler.api.service.TaskInstanceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author xujie
 * @description homepageService
 * @create 2021-10-08 15:13
 **/
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private TaskInstanceService taskInstanceService;
    @Override
    public BusinessResult<Boolean> homePage(SchedulerHomepageRequest request) {
        //前三天0点
        Date threeDayAgo = DateUtils.getFirstSecondOfDate(DateUtils.addDate(new Date(), -3));
        //前一天24点
        Date oneDayAgo = DateUtils.getLastSecondOfDate(DateUtils.addDate(new Date(), -1));
        return BusinessResult.success(true);
    }
}
