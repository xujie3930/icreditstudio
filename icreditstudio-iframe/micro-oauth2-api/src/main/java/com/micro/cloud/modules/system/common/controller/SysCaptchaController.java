package com.micro.cloud.modules.system.common.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.common.service.SysCaptchaService;
import com.micro.cloud.modules.system.util.VerifyCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/** @author roy */
@Api(tags = "验证码")
@Validated
@RestController
@RequestMapping("/sys/captcha")
public class SysCaptchaController {

  @Autowired private SysCaptchaService captchaService;

  @ApiOperation("生成图片验证码")
  @GetMapping("/get-image")
  public CommonResult<String> getCaptchaImage() {
    try {
      String result = this.captchaService.getCaptchaImage();
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /* 获取验证码图片（纯数字）*/

  @GetMapping("/get-imageForNum")
  @ApiOperation("生成纯数字图片验证码")
  public CommonResult<String> getVerificationCode() {

    try {
      String result = this.captchaService.getCaptchaImageForNum();
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }

  }
}
