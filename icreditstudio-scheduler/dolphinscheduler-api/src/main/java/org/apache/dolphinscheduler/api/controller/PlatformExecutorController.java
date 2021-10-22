package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.param.ExecPlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.request.ExecPlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.api.service.PlatformExecutorService;
import org.apache.dolphinscheduler.api.service.SchedulerService;
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
    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/execProcessInstance")
    public BusinessResult<Boolean> execProcessInstance(@RequestBody ExecPlatformProcessDefinitionRequest request) throws ParseException {
        ExecPlatformProcessDefinitionParam param = new ExecPlatformProcessDefinitionParam();
        BeanCopyUtils.copyProperties(request, param);
        return platformExecutorService.execProcessInstance(param);
    }

    @GetMapping("/execSyncTask")
    public Boolean execSyncTask(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("execType") int execType) throws ParseException {
        if(0 == execType){//手动执行
            ExecPlatformProcessDefinitionParam param = new ExecPlatformProcessDefinitionParam();
            param.setWorkerGroup("default");
            param.setTimeout(86400);
            param.setProcessDefinitionId(processDefinitionId);
            platformExecutorService.manualExecSyncTask(param);
        }else{//周期执行
            schedulerService.onlineByProcessDefinitionId(processDefinitionId);
        }
        return true;
    }

}
