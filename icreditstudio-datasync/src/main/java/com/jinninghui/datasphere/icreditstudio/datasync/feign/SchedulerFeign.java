package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignCreatePlatformProcessDefinitionRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignUpdatePlatformProcessDefinitionRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.result.CreatePlatformTaskResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

/**
 * @author Peng
 */
@FeignClient(value = "dolphinscheduler")
public interface SchedulerFeign {

    /**
     * 创建任务工作流
     *
     * @param request
     * @return
     */
    @PostMapping("/dolphinscheduler/platform/task/create")
    BusinessResult<CreatePlatformTaskResult> create(@RequestBody FeignCreatePlatformProcessDefinitionRequest request);

    /**
     * 执行任务
     * @param processDefinitionId
     * @param execType 0 -- 手动任务 ， 1 -- 周期任务
     * @return
     */
    @GetMapping(value = "/dolphinscheduler/platform/exec/execSyncTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    String execSyncTask(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("execType") int execType);

    /**
     * 停止任务
     * @param processDefinitionId
     * @return
     */
    @GetMapping(value = "/dolphinscheduler/platform/exec/stopSyncTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    String stopSyncTask(@RequestParam("processDefinitionId") String processDefinitionId);

    /**
     * 删除任务
     * @param processDefinitionId
     * @return 1 表示该任务有实例在运行，不能删除  0 表示删除成功
     */
    @GetMapping(value = "/dolphinscheduler/platform/exec/deleteSyncTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    String deleteSyncTask(@RequestParam("processDefinitionId") String processDefinitionId);

    /**
     * 更新任务工作流
     *
     * @param request
     * @return
     */
    @PostMapping("/dolphinscheduler/platform/task/update")
    BusinessResult<Boolean> update(@RequestBody FeignUpdatePlatformProcessDefinitionRequest request);
}
