package com.micro.cloud.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.domian.dto.Oauth2TokenDto;
import com.micro.cloud.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口 Created by xulei on 2021/11/3
 */
@Api(tags = "OAuth2 登录接口")
@RestController
@RequestMapping("/oauth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private UserInfoService userFunctionService;

    /**
     * Oauth2登录认证
     */
    @ApiOperation(value = "Oauth2 获取token令牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", value = "客户端id", required = true),
            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端密钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新后token", required = false),
            @ApiImplicitParam(name = "username", value = "用户名", required = true)
    })
    @PostMapping(value = "/token")
    public CommonResult<Oauth2TokenDto> postAccessToken(
            Principal principal, @RequestParam Map<String, String> parameters) {
        try {
            logger.info("####### parameters:{}", parameters);
            OAuth2AccessToken oAuth2AccessToken =
                    tokenEndpoint.postAccessToken(principal, parameters).getBody();
            Oauth2TokenDto oauth2TokenDto =
                    new Oauth2TokenDto(
                            oAuth2AccessToken.getValue(),
                            oAuth2AccessToken.getRefreshToken().getValue(),
                            "Bearer ",
                            oAuth2AccessToken.getExpiresIn());
            return CommonResult.success(oauth2TokenDto);
        } catch (HttpRequestMethodNotSupportedException e) {
            e.printStackTrace();
            // todo 登录失败日志是否添加？
            return CommonResult.failed("获取token失败");
        }
    }
}
