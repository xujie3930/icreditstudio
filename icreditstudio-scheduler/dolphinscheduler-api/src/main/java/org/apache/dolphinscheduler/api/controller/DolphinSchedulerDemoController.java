package org.apache.dolphinscheduler.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.api.enums.ExecuteType;
import org.apache.dolphinscheduler.api.request.InstanceCreateRequest;
import org.apache.dolphinscheduler.api.service.DolphinSchedulerDemoService;
import org.apache.dolphinscheduler.api.service.ExecutorService;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
public class DolphinSchedulerDemoController {

    @Autowired
    private DolphinSchedulerDemoService dolphinSchedulerDemoService;

    @Autowired
    private ExecutorService execService;

    @PostMapping("/dol/demo/test")
    public String test(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @RequestBody JSONObject jsonObject) throws Exception {
//        DolParam param = new DolParam();
//        BeanUtils.copyProperties(dolRequest, param);

        dolphinSchedulerDemoService.test(loginUser, jsonObject);
        return "SUCCESS";
    }

    @PostMapping("/dol/demo/dataxTest")
    public Map<String,String> dataxTest(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @RequestBody InstanceCreateRequest request) throws Exception {
        return dolphinSchedulerDemoService.dataxTest(loginUser, request);
    }

    /**
     * 重跑：executeType:=REPEAT_RUNNING
     *恢复失败：ecuteType=START_FAILURE_TASK_PROCESS
     * @param loginUser
     * @param processDefinitionId
     * @param executeType
     * @return
     */
    @PostMapping(value = "/execute")
    public Map<String, Object> execute(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                          @RequestParam("processDefinitionId") Integer processDefinitionId,
                          @RequestParam("executeType") ExecuteType executeType
    ) {
        Map<String, Object> result = execService.newExecute(loginUser, null, processDefinitionId, executeType);
        return result;
    }

}
