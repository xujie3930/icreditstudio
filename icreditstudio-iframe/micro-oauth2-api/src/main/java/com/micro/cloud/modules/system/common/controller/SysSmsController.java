package com.micro.cloud.modules.system.common.controller;

import static com.micro.cloud.util.servlet.ServletUtils.getClientIP;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.common.service.SysSmsCodeService;
import com.micro.cloud.modules.system.common.vo.SysAuthSendSmsReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author roy */
@Api(tags = "短信验证码")
@RestController
@RequestMapping("/sys/sms")
public class SysSmsController {

  private final SysSmsCodeService smsCodeService;

  public SysSmsController(SysSmsCodeService smsCodeService) {
    this.smsCodeService = smsCodeService;
  }

  @PostMapping("/send-sms-code")
  @ApiOperation("发送手机验证码")
  public CommonResult<Boolean> sendSmsCode(@RequestBody @Valid SysAuthSendSmsReqVO reqVO) {
    try {
      smsCodeService.sendSmsCode(reqVO.getMobile(), reqVO.getScene(), getClientIP());
      return CommonResult.success(true);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }
}
