package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.HomePageService;
import org.apache.dolphinscheduler.api.service.result.TaskRough;
import org.apache.dolphinscheduler.api.service.result.TaskSituation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xujie
 * @description 调度中心首页
 * @create 2021-10-08 14:23
 **/

@RestController
@RequestMapping("/homepage")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @PostMapping(value = "rough")
    public BusinessResult<TaskRough> rough(@RequestBody SchedulerHomepageRequest request) {
        return homePageService.rough(request);
    }

    @PostMapping(value = "/situation/today")
    public BusinessResult<List<TaskSituation>> situation(@RequestBody SchedulerHomepageRequest request) {
        return homePageService.situation(request.getWorkspaceId());
    }
}
