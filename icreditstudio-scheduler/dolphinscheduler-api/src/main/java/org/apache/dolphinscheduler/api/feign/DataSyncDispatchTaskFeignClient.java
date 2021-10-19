package org.apache.dolphinscheduler.api.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lmh
 * @create 2021-10-12 10:47
 **/
@FeignClient("datasync")
public interface DataSyncDispatchTaskFeignClient {

    /**
     * 获取周期任务列表
     */
    @PostMapping("/datasync/dispatchPage")
    BusinessResult<BusinessPageResult<DispatchTaskPageResult>> dispatchPage(@RequestBody DispatchTaskPageParam param) ;

    /**
     * 获取任务中的 流程定义ID
     */
    @GetMapping("/datasync/getProcessInstanceId")
    String getProcessInstanceIdByTaskId(@RequestParam("taskId") String taskId);
}