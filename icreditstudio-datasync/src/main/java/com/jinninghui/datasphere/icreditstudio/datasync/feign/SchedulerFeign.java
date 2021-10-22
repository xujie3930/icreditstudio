package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "dolphinscheduler")
public interface SchedulerFeign {

    @GetMapping("/dolphinscheduler/platform/exec/execSyncTask")
    Boolean execSyncTask(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("execType") int execType);

}
