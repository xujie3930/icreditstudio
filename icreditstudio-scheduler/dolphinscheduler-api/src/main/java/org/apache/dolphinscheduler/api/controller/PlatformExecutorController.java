package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.param.ExecPlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.request.ExecPlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.api.service.PlatformExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @author Peng
 */
@Slf4j
@RestController
@RequestMapping("/platform/exec")
public class PlatformExecutorController {

    @Autowired
    private PlatformExecutorService platformExecutorService;

    @PostMapping("/execProcessInstance")
    public BusinessResult<Boolean> execProcessInstance(@RequestBody ExecPlatformProcessDefinitionRequest request) throws ParseException {
        ExecPlatformProcessDefinitionParam param = new ExecPlatformProcessDefinitionParam();
        BeanCopyUtils.copyProperties(request, param);
        return platformExecutorService.execProcessInstance(param);
    }

    @GetMapping("/execSyncTask")
    public Boolean execSyncTask(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("execType") int execType) throws ParseException {
        platformExecutorService.execSyncTask(processDefinitionId, execType);
        return true;
    }

    @GetMapping("/stopSyncTask")
    public Boolean stopSyncTask(@RequestParam("processDefinitionId") String processDefinitionId) {
        platformExecutorService.stopSyncTask(processDefinitionId);
        return true;
    }

    @GetMapping("/deleteSyncTask")
    public void deleteSyncTask(@RequestParam("processDefinitionId") String processDefinitionId) {
        platformExecutorService.deleteSyncTask(processDefinitionId);
    }

}
