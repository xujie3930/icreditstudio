package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.api.param.CreatePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.request.CreatePlatformProcessDefinitionRequest;
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

    @PostMapping("/create")
    public BusinessResult<CreatePlatformTaskResult> create(@RequestBody CreatePlatformProcessDefinitionRequest request) {
        CreatePlatformProcessDefinitionParam param = new CreatePlatformProcessDefinitionParam();
        BeanCopyUtils.copyProperties(request, param);
        return platformProcessDefinitionService.create(param);
    }
}
