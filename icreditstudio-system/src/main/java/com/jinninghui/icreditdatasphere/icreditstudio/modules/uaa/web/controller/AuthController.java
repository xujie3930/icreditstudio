package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.web.controller;

import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.TokenService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result.BusinessToken;
import com.hashtech.businessframework.log.Logable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  @author jidonglin
 * 	  鉴权Controller
 *
 */
@RestController
@Resource
@Api(value = "鉴权控制器")
public class AuthController {

	@Autowired
	TokenService tokenService;



	/**
	 * token鉴权
	 * 鉴权失败抛出异常
	 * @param interfaceUrl
	 * @param interfaceMethod
	 * @param token
	 * @return
	 */
	@Logable
	@PostMapping(value = "/tokenAuth")
	@ApiOperation(value = "token鉴权，仅API网关调用", httpMethod = "POST")
	public BusinessToken tokenAuth(@RequestParam String interfaceUrl, @RequestParam String interfaceMethod,
								   @RequestParam String token, @RequestParam String requestType) {
		return tokenService.tokenAuth(interfaceUrl, interfaceMethod, token,requestType);
	}


	@Logable
	@DeleteMapping(value = "/token")
//	@Exceptionable
	@ApiOperation(value = "删除令牌", notes = "删除令牌，仅内部使用，不可对外开放", httpMethod = "DELETE")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String", paramType = "query", length = 50) })
	public String removeToken(@RequestParam String token) {
		return tokenService.removeToken(token);
	}
}
