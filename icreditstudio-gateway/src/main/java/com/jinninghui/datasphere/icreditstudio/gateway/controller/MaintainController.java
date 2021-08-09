package com.jinninghui.datasphere.icreditstudio.gateway.controller;

import com.jinninghui.datasphere.icreditstudio.gateway.service.RespCodeMsgMappingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "MaintainController")
public class MaintainController {
	
	@Autowired
	RespCodeMsgMappingService respCodeMsgMappingService;
	
	/**
	 * 此接口便于分析API网关翻译错误码失败的原因
	 * @param errorCode
	 * @return
	 */
	@RequestMapping(value = "/getCodeMessage", method = RequestMethod.GET)
	public String getCodeMapping(@RequestParam(value = "errorCode", required = true) String errorCode) {
    	
    	return respCodeMsgMappingService.getMsgByCode(errorCode);
		
	}
	
}
