package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignCreatePlatformProcessDefinitionRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.result.CreatePlatformTaskResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Peng
 */
@FeignClient(value = "dolphinscheduler")
public interface SchedulerFeign {

    /**
     * 获取连接信息
     *
     * @param request
     * @return
     */
    @PostMapping("/platform/task/create")
    BusinessResult<CreatePlatformTaskResult> create(@RequestBody FeignCreatePlatformProcessDefinitionRequest request);

    @GetMapping("/dolphinscheduler/platform/exec/execSyncTask")
    Boolean execSyncTask(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("execType") int execType);

}
