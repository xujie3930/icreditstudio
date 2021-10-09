package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xujie
 * @description 调度中心首页
 * @create 2021-10-08 14:23
 **/

@RestController
@RequestMapping()
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @PostMapping(value = "/homepage")
    public BusinessResult<Boolean> homePage(@RequestBody SchedulerHomepageRequest request) {
        return homePageService.homePage(request);
    }

}
