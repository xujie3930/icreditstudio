package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.HomePageService;
import org.apache.dolphinscheduler.api.service.result.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/rough")
    public BusinessResult<TaskRoughResult> rough(@RequestHeader(value = "x-userid") String userId, @RequestBody SchedulerHomepageRequest request) {
        return homePageService.rough(userId, request);
    }

    @PostMapping(value = "/situation")
    public BusinessResult<List<TaskSituationResult>> situation(@RequestHeader(value = "x-userid") String userId, @RequestBody SchedulerHomepageRequest request) {
        return homePageService.situation(userId, request.getWorkspaceId());
    }

    @PostMapping(value = "/taskCount")
    public BusinessResult<List<TaskCountResult>> taskCount(@RequestHeader(value = "x-userid") String userId, @RequestBody SchedulerHomepageRequest request) {
        return homePageService.taskCount(userId, request);
    }

    @PostMapping(value = "/runtimeRank")
    public BusinessResult<List<RuntimeRankResult>> runtimeRank(@RequestHeader(value = "x-userid") String userId, @RequestBody SchedulerHomepageRequest request) {
        return homePageService.runtimeRank(userId, request);
    }

    @PostMapping(value = "/runErrorRank")
    public BusinessResult<List<RunErrorRankResult>> runErrorRank(@RequestHeader(value = "x-userid") String userId, @RequestBody SchedulerHomepageRequest request) {
        return homePageService.runErrorRank(userId, request);
    }

    @GetMapping(value = "/workbench")
    public WorkBenchResult workbench(@RequestParam("userId") String userId, @RequestParam("id") String id){
        return homePageService.workbench(userId, id);
    }

}
