package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.param.LogPageParam;
import org.apache.dolphinscheduler.api.request.DispatchTaskPageRequest;
import org.apache.dolphinscheduler.api.request.ExecInstanceRequest;
import org.apache.dolphinscheduler.api.request.LogPageRequest;
import org.apache.dolphinscheduler.api.service.DispatchService;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.apache.dolphinscheduler.common.vo.DispatchLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 同步任务调度
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
    public BusinessResult<BusinessPageResult<DispatchTaskPageResult>> dispatchPage(@RequestBody DispatchTaskPageRequest dispatchPageRequest){
        DispatchTaskPageParam param = new DispatchTaskPageParam();
        BeanCopyUtils.copyProperties(dispatchPageRequest, param);
        return dispatchService.dispatchPage(param);
    }

    /**
     * 同步任务重跑或终止
     * @param request
     * @return
     */
    @PostMapping("/execInstance")
    public BusinessResult<Boolean> reStartOrStop(@RequestBody ExecInstanceRequest request){
        return dispatchService.reStartOrStop(request.getProcessInstanceId(), request.getTaskInstanceId(), request.getExecType());
    }

    /**
     * 同步任务调度 -- 立即执行
     *
     * @param taskId 数据同步任务ID
     * @return
     */
    @GetMapping("/schedule/nowRun")
    public BusinessResult<Boolean> nowRun(@RequestParam("taskId") String taskId) {
        return dispatchService.nowRun(taskId);
    }

    @PostMapping("/log/page")
    public BusinessResult<BusinessPageResult<DispatchLogVO>> logPage(@RequestBody LogPageRequest request){
        LogPageParam param = new LogPageParam();
        BeanCopyUtils.copyProperties(request, param);
        if(null != request.getExecTimeStart()){
            param.setExecTimeStart(new Date(request.getExecTimeStart()));
        }
        if(null != request.getExecTimeEnd()){
            param.setExecTimeEnd(new Date(request.getExecTimeEnd()));
        }
        return dispatchService.logPage(param);
    }

}
