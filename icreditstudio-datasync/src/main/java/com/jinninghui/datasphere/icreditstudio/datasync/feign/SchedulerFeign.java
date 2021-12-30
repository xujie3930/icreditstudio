package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignPlatformProcessDefinitionRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignUpdatePlatformProcessDefinitionRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.result.CreatePlatformTaskResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping(value = "/dolphinscheduler/platform/task/create")
    BusinessResult<CreatePlatformTaskResult> create(@RequestBody FeignPlatformProcessDefinitionRequest request);


    /**
     * 周期执行
     *
     * @param processDefinitionId
     * @return
     */
    @GetMapping("/dolphinscheduler/platform/exec/execCycle")
    BusinessResult<Boolean> execCycle(@RequestParam("processDefinitionId") String processDefinitionId);

    /**
     * 执行任务
     *
     * @param processDefinitionId
     * @return
     */
    @GetMapping(value = "/dolphinscheduler/platform/exec/execSyncTask")
    void execSyncTask(@RequestParam("processDefinitionId") String processDefinitionId);

    /**
     * 停止任务
     *
     * @param processDefinitionId
     * @return
     */
    @GetMapping(value = "/dolphinscheduler/platform/exec/stopSyncTask", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    String stopSyncTask(@RequestParam("processDefinitionId") String processDefinitionId);

    /**
     * 删除任务
     *
     * @param processDefinitionId
     * @return 1 表示该任务有实例在运行，不能删除  0 表示删除成功
     */
    @GetMapping(value = "/dolphinscheduler/platform/exec/deleteSyncTask", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    String deleteSyncTask(@RequestParam("processDefinitionId") String processDefinitionId);

    /**
     * 更新任务工作流
     *
     * @param request
     * @return
     */
    @PostMapping("/dolphinscheduler/platform/task/update")
    BusinessResult<Boolean> update(@RequestBody FeignPlatformProcessDefinitionRequest request);

    @GetMapping(value = "/dolphinscheduler/platform/exec/enableSyncTask", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    String enableSyncTask(@RequestParam("processDefinitionId") String processDefinitionId);

    @GetMapping(value = "/dolphinscheduler/platform/exec/ceaseSyncTask", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    String ceaseSyncTask(@RequestParam("processDefinitionId") String processDefinitionId);

    @GetMapping(value = "/dolphinscheduler/definition/updateDefinitionVersionById")
    void updateDefinitionVersionById(@RequestParam("version") Integer version, @RequestParam("processDefinitionId") String processDefinitionId);
}
