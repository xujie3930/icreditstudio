package com.jinninghui.datasphere.icreditstudio.modules.uaa.web.controller;

import com.jinninghui.datasphere.icreditstudio.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.SessionService;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.OperateLoginResponse;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.web.request.OperateLoginRequest;
import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.CommonOuterResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jidonglin 系统用户管理
 */
@RestController
@RefreshScope
@RequestMapping("/uaa/user")
@Api(value = "UserController", description = "系统用户管理")
public class UserController {

    @Autowired
    private SessionService sessionService;

    /**
     * 用户退出登录
     */
    @Log(type = Log.Type.LOGIN,operateType = Log.OperateType.LOGOUT)
    @PostMapping("Session/logout")
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @Logable
    public CommonOuterResponse logout(
            @RequestHeader(value = "x-userid", required = false) String userId,
            @RequestHeader(value = "x-token", required = false) String token) {
        sessionService.logout(token);
        return new CommonOuterResponse();
    }

    /**
     * 后台用户登录
     */
    @Log(type = Log.Type.LOGIN, operateType = Log.OperateType.LOGIN)
    @PostMapping("/operateLogin")
    @ApiOperation(value = "后台用户登录", notes = "后台用户登录", httpMethod = "POST")
    @Logable
    public BusinessResult<OperateLoginResponse> operateLogin(@RequestBody OperateLoginRequest request,
                                                             HttpServletResponse response, HttpServletRequest servletRequest) throws IOException {
        BusinessResult<OperateLoginResponse> operateLoginResponse = sessionService.backstageUserLogin(request.getLoginName(), request.getPassword(), request.getApplicationId());
        Cookie cookie = new Cookie("token", operateLoginResponse.getData().getToken());
        cookie.setPath("/");
        response.addCookie(cookie);
        return operateLoginResponse;
    }

    //1、/uaa/auth/token (POST) (userName、password) （accessToken、validTime=8小时 8*60*60）app\H5\xiaochengxu 用户、测试人员、运营人员
    //2、/uaa/auth/token/refresh () token鉴权  （accessToken、validTime=8小时 8*60*60）
}
