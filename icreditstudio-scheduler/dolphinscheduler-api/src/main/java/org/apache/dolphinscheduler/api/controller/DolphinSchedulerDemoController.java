package org.apache.dolphinscheduler.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.api.service.DolphinSchedulerDemoService;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
public class DolphinSchedulerDemoController {

    @Autowired
    private DolphinSchedulerDemoService dolphinSchedulerDemoService;

    @PostMapping("/dol/demo/test")
    public String test(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @RequestBody JSONObject jsonObject) throws Exception {
//        DolParam param = new DolParam();
//        BeanUtils.copyProperties(dolRequest, param);

        dolphinSchedulerDemoService.test(loginUser, jsonObject);
        return "SUCCESS";
    }

    @PostMapping("/dol/demo/dataxTest")
    public Map<String,String> dataxTest(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @RequestBody JSONObject jsonObject) throws Exception {
        return dolphinSchedulerDemoService.dataxTest(loginUser, jsonObject);
    }

}
