package com.micro.cloud.modules.system.log.controller;

import com.micro.cloud.domian.dto.SysLoginLogCreateReqDTO;
import com.micro.cloud.modules.system.log.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录日志(目前提供给OAuth2模块使用)")
@RestController
@RequestMapping("/sys/login-log")
@Validated
public class SysLoginLogController {

  @Resource private SysLoginLogService loginLogService;

  @ApiOperation(value = "创建用户登录日志")
  @PostMapping("/create")
  public void create(@RequestBody SysLoginLogCreateReqDTO reqDTO) {
    loginLogService.createLoginLog(reqDTO);
  }
}
