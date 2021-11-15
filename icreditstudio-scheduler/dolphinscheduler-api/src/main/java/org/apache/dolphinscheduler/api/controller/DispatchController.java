package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.param.LogPageParam;
import org.apache.dolphinscheduler.api.request.DispatchTaskPageRequest;
import org.apache.dolphinscheduler.api.request.LogPageRequest;
import org.apache.dolphinscheduler.api.service.DispatchService;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.apache.dolphinscheduler.common.utils.DateUtils;
import org.apache.dolphinscheduler.common.utils.StringUtils;
import org.apache.dolphinscheduler.common.vo.DispatchLogVO;
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
    public BusinessResult<BusinessPageResult<DispatchTaskPageResult>> dispatchPage(@RequestBody DispatchTaskPageRequest dispatchPageRequest){
        DispatchTaskPageParam param = new DispatchTaskPageParam();
        BeanCopyUtils.copyProperties(dispatchPageRequest, param);
        return dispatchService.dispatchPage(param);
    }

    /**
     *
     * @param processInstanceId
     * @param execType   执行类型 ：0 表示 重跑，1 表示 终止
     * @return
     */
    @GetMapping("/execInstance")
    public BusinessResult<Boolean> startOrStop(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("execType") String execType){
        return dispatchService.startOrStop(processInstanceId, execType);
    }

    @PostMapping("/log/page")
    public BusinessResult<BusinessPageResult<DispatchLogVO>> logPage(@RequestBody LogPageRequest request){
        LogPageParam param = new LogPageParam();
        BeanCopyUtils.copyProperties(request, param);
        StringBuilder dateStr = null;
        if(StringUtils.isNotEmpty(request.getExecTimeStart())){
            dateStr = new StringBuilder(request.getExecTimeStart());
            dateStr.append(" 00:00:00");
            param.setExecTimeStart(DateUtils.parse(String.valueOf(dateStr), "yyyy-MM-dd HH:mm:ss"));
        }
        if(StringUtils.isNotEmpty(request.getExecTimeEnd())){
            dateStr = new StringBuilder(request.getExecTimeEnd());
            dateStr.append(" 23:59:59");
            param.setExecTimeEnd(DateUtils.parse(String.valueOf(dateStr), "yyyy-MM-dd HH:mm:ss"));
        }
        return dispatchService.logPage(param);
    }

}
