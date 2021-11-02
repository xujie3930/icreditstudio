package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.param.CreatePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.param.DeletePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.param.ReleasePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.param.UpdatePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.request.CreatePlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.api.request.DeletePlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.api.request.ReleasePlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.api.request.UpdatePlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.api.service.PlatformProcessDefinitionService;
import org.apache.dolphinscheduler.api.service.result.CreatePlatformTaskResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Peng
 */
@Slf4j
@RestController
@RequestMapping("/platform/task")
public class PlatformTaskController {

    @Resource
    private PlatformProcessDefinitionService platformProcessDefinitionService;

    @Logable
    @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
    public BusinessResult<CreatePlatformTaskResult> create(@RequestBody CreatePlatformProcessDefinitionRequest request) {
        CreatePlatformProcessDefinitionParam param = new CreatePlatformProcessDefinitionParam();
        BeanCopyUtils.copyProperties(request, param);
        return BusinessResult.success(platformProcessDefinitionService.create(param));
    }

    @Logable
    @PostMapping("/release")
    public BusinessResult<Boolean> release(@RequestBody ReleasePlatformProcessDefinitionRequest request) {
        ReleasePlatformProcessDefinitionParam param = new ReleasePlatformProcessDefinitionParam();
        BeanCopyUtils.copyProperties(request, param);
        return platformProcessDefinitionService.release(param);
    }

    @Logable
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@RequestBody DeletePlatformProcessDefinitionRequest request) {
        DeletePlatformProcessDefinitionParam param = new DeletePlatformProcessDefinitionParam();
        BeanCopyUtils.copyProperties(request, param);
        return platformProcessDefinitionService.delete(param);
    }

    @Logable
    @PostMapping("/update")
    public BusinessResult<Boolean> update(@RequestBody UpdatePlatformProcessDefinitionRequest request) {
        UpdatePlatformProcessDefinitionParam param = new UpdatePlatformProcessDefinitionParam();
        BeanCopyUtils.copyProperties(request, param);
        return platformProcessDefinitionService.update(param);
    }
}
