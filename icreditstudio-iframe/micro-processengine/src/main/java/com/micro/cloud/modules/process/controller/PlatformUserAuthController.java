package com.micro.cloud.modules.process.controller;

import com.alibaba.fastjson.JSONObject;
import com.micro.cloud.util.file.Md5Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class PlatformUserAuthController {

    private final Logger logger = LoggerFactory.getLogger(PlatformUserAuthController.class);


    @Value("${oa.tokenUrl}")
    private String oaTokenUrl;

    @Value("${oa.singleLogin}")
    private String singleLoginUrl;

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 项目管理单点登录到OA
     */

    @GetMapping("/project/singleLogin")
    @ApiOperation(value = "项目管理单点登录到OA", notes = "项目管理单点登录到OA", httpMethod = "GET")
    public String singleLogin(@ApiParam(value = "uid", required = true) String uid, HttpServletResponse response) throws IOException {
        logger.info("跳转参数"+uid);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String rtime = sdf.format(new Date());
        params.add("uid", uid);
        params.add("rtime", rtime);
        params.add("key", Md5Utils.md5Password(uid + rtime));
        String result = restTemplate.postForObject(oaTokenUrl, params, String.class);
        JSONObject resObj = JSONObject.parseObject(result);
        String token=resObj.getString("token");

        String requestUrl=singleLoginUrl+"?token="+token+"&uid="+uid;

        return requestUrl;



    }
}
