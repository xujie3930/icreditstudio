package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.request.DispatchTaskPageRequest;
import org.apache.dolphinscheduler.api.service.DispatchService;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 调度中心
 */
@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    /**
     * 同步任务调度分页列表
     * @param dispatchPageRequest
     * @return
     */
    @PostMapping("/page")
    public BusinessPageResult<DispatchTaskPageResult> dispatchPage(@RequestBody DispatchTaskPageRequest dispatchPageRequest){
        DispatchTaskPageParam param = new DispatchTaskPageParam();
        BeanCopyUtils.copyProperties(dispatchPageRequest, param);
        return dispatchService.dispatchPage(param);
    }

    /**
     *
     * @param taskId
     * @param execType   执行类型 ：0 表示 重跑，1 表示 终止
     * @return
     */
    @GetMapping("/execInstance")
    public BusinessResult<Boolean> startOrStop(@RequestParam("taskId") String taskId, @RequestParam("execType") String execType){
        return dispatchService.startOrStop(taskId, execType);
    }

}
