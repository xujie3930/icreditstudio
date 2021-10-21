package com.jinninghui.datasphere.icreditstudio.datasync.feign;

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
}
