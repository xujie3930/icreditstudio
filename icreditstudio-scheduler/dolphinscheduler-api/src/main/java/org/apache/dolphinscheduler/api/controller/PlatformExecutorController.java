package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.param.ExecPlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.request.ExecPlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.api.service.PlatformExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/execSyncTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    public String execSyncTask(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("execType") int execType) {
        try {
            platformExecutorService.execSyncTask(processDefinitionId, execType);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "true";
    }

    @GetMapping(value = "/stopSyncTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    public String stopSyncTask(@RequestParam("processDefinitionId") String processDefinitionId) {
        platformExecutorService.stopSyncTask(processDefinitionId);
        return "true";
    }

    @GetMapping(value = "/deleteSyncTask",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = "application/json;charset=UTF-8")
    public String deleteSyncTask(@RequestParam("processDefinitionId") String processDefinitionId) {
        return platformExecutorService.deleteSyncTask(processDefinitionId);
    }

}
