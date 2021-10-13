package org.apache.dolphinscheduler.api.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.request.DispatchTaskPageRequest;
import org.apache.dolphinscheduler.api.service.DispatchService;
import org.apache.dolphinscheduler.api.vo.DispatchTaskPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调度中心
 */
@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    /**
     * 周期任务分页列表
     * @param dispatchPageRequest
     * @return
     */
    @PostMapping("/page")
    public BusinessPageResult<DispatchTaskPageVO> dispatchPage(@RequestBody DispatchTaskPageRequest dispatchPageRequest){
        DispatchTaskPageParam param = new DispatchTaskPageParam();
        BeanCopyUtils.copyProperties(dispatchPageRequest, param);
        BusinessPageResult<DispatchTaskPageVO> resultPage = dispatchService.dispatchPage(param);
        return resultPage;
    }

}
