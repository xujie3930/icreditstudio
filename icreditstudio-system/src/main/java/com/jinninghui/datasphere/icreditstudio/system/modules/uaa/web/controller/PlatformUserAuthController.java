package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.web.controller;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.SessionService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.PlatformUserAuthResponse;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.web.request.PlatFromLoginRequest;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @author 用户鉴权
 */
@RestController
@RefreshScope
@RequestMapping("/uaa/auth")
@Api(value = "platformUserAuthController", description = "用户鉴权")
public class PlatformUserAuthController {

    @Autowired
    private SessionService sessionService;

    /**
     *   1、/uaa/auth/token (POST) (userName、password) （accessToken、validTime=8小时 8*60*60）app\H5\xiaochengxu 用户、测试人员、运营人员
     *   2、/uaa/auth/token/refresh () token鉴权  （accessToken、validTime=8小时 8*60*60）
     */
    /**
     * 用户登录
     */
    @PostMapping("/token")
    @ApiOperation(value = "外部用户登录获取token", notes = "外部用户登录获取token", httpMethod = "POST")
    @Logable
    public BusinessResult<PlatformUserAuthResponse> getToken(@RequestBody PlatFromLoginRequest request) {

        return sessionService.getToken(request.getUserName(), request.getPassword());
    }

    /**
     * 刷新token过期时间
     */
    @PostMapping("/token/refresh")
    @ApiOperation(value = "刷新token过期时间", notes = "刷新token过期时间", httpMethod = "POST")
    @Logable
    public BusinessResult<PlatformUserAuthResponse> refreshToken(@RequestHeader(value = "x-Access-Token") String token) {

        return sessionService.refreshToken(token);
    }


}
