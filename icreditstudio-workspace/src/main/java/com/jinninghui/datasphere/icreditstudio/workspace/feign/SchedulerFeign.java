package com.jinninghui.datasphere.icreditstudio.workspace.feign;

import com.jinninghui.datasphere.icreditstudio.workspace.web.result.WorkBenchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xujie
 * @description 远程scheduler的feign调用
 * @create 2021-11-17 15:36
 **/
@FeignClient(value = "dolphinscheduler")
public interface SchedulerFeign {

    @GetMapping(value = "/dolphinscheduler/homepage/workbench",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    WorkBenchResult workbench(@RequestParam("userId") String userId, @RequestParam("id") String id);
}
